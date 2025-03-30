package com.romoreno.dentalium.application.services;

import com.romoreno.dentalium.application.mappers.BudgetMapperTemp;
import com.romoreno.dentalium.application.utils.Utils;
import com.romoreno.dentalium.domain.port.in.BudgetPort;
import com.romoreno.dentalium.domain.port.out.persistence.*;
import com.romoreno.dentalium.domain.port.out.report.ReportGenerator;
import com.romoreno.dentalium.domain.port.out.signer.Signer;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.budget.dto.*;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.BudgetEntity;
import com.romoreno.dentalium.infraestructure.config.annotations.GetTransaction;
import com.romoreno.dentalium.infraestructure.config.annotations.PortImplementation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@PortImplementation
public class BudgetService implements BudgetPort {

    private final DataConnectionProvider dataConnectionProvider;
    private final ReportGenerator reportGenerator;
    private final BudgetRepository budgetRepository;
    private final BudgetLineRepository budgetLineRepository;
    private final BudgetStatusRepository budgetStatusRepository;
    private final BudgetMapperTemp budgetMapperTemp;
    private final UserRepository userRepository;
    private final Signer signer;
    private final LocalContainerEntityManagerFactoryBean entityManagerFactory2;

    public BudgetService(DataConnectionProvider dataConnectionProvider, ReportGenerator reportGenerator,
                         BudgetRepository budgetRepository, BudgetLineRepository budgetLineRepository,
                         BudgetStatusRepository budgetStatusRepository, BudgetMapperTemp budgetMapperTemp,
                         UserRepository userRepository, Signer signer, LocalContainerEntityManagerFactoryBean entityManagerFactory2) {
        this.dataConnectionProvider = dataConnectionProvider;
        this.reportGenerator = reportGenerator;
        this.budgetRepository = budgetRepository;
        this.budgetLineRepository = budgetLineRepository;
        this.budgetStatusRepository = budgetStatusRepository;
        this.budgetMapperTemp = budgetMapperTemp;
        this.userRepository = userRepository;
        this.signer = signer;
        this.entityManagerFactory2 = entityManagerFactory2;
    }

    @Override
    @GetTransaction
    public boolean saveBudget(BudgetRequestRestDTO budget) {
        if (budget.getId() != null) {
            final var budgetEntity = budgetRepository.findById(budget.getId());
            if (budgetEntity.isPresent()) {
                budgetEntity.get().getBudgetLineEntityList().clear();
                budget.getTreatments().stream()
                        .map(v -> budgetMapperTemp.toEntity(v, budgetEntity.get()))
                        .forEach(v -> budgetEntity.get().getBudgetLineEntityList().add(v));
                budgetRepository.save(budgetEntity.get());
                return true;
            }
        } else {
            final var patient = userRepository.findPatient(budget.getIdPatient());
            final var status = budgetStatusRepository.find(1);

            if (patient.isPresent() && status.isPresent()) {
                var budgetEntity = budgetRepository.save(budgetMapperTemp.toEntity(budget, status.get(), patient.get()));
                budgetEntity.setBudgetLineEntityList(budget.getTreatments().stream()
                        .map(v -> budgetMapperTemp.toEntity(v, budgetEntity))
                        .toList());
                return true;
            }
        }
        return false;
    }

    @Override
    @GetTransaction
    public boolean includeTreatment(String id, BudgetLineRestDTO budgetLine) {
        budgetRepository.findById(Integer.parseInt(id))
                .ifPresent(v -> {
                    final var line = budgetMapperTemp.toEntity(Integer.parseInt(id), budgetLine);
                    v.getBudgetLineEntityList().add(line);
                    budgetRepository.save(v);
                });

        return true;
    }

    @Override
    @GetTransaction
    public boolean deleteTreatment(String budgetId, String treatmentId) {
        budgetLineRepository.delete(budgetId, treatmentId);
        return true;
    }

    @Override
    @GetTransaction
    public boolean deleteBudget(String id) {
        budgetRepository.delete(id);
        return true;
    }

    @Override
    @GetTransaction
    public boolean modifyStatus(ModifyStatusBudgetRequest request) {
        final var budgetStatusOpt = budgetStatusRepository.find(request.getStatusCode());

        if (budgetStatusOpt.isPresent()) {
            budgetRepository.findById(request.getId())
                    .ifPresent(v -> v.setBudgetStatusEntityId(budgetStatusOpt.get()));
            return true;
        }

        return false;
    }

    @Override
    public BudgetResponse listBudgets(String patientId) {
        final var budgetList = budgetRepository.findAllByPatientId(patientId);
        return budgetMapperTemp.toAPI(patientId, budgetList);
    }

    @Override
    public BudgetWithBudgetLinesResponse detailBudget(String id) {
        final var budget = budgetRepository.findById(Integer.parseInt(id));
        return budget.map(budgetMapperTemp::toAPIWithBudgetLines).orElse(null);
    }

    @Override
    public byte[] generatePdfBudget(int id, boolean signed) throws Exception {

        final var budgetOpt = budgetRepository.findById(id);
        if (budgetOpt.isEmpty()) {
            return null;
        }
        final var budget = budgetOpt.get();

        final var teethList = this.getImplicatedTeeth(budget);

        final var teethImage = this.getTeethImage(teethList);

        final var reportParams = new HashMap<String, Object>();
        reportParams.put("BUDGET_ID", id);
        reportParams.put("TEETH_IMAGE", new ByteArrayInputStream(teethImage));
        reportParams.put("DATE", new SimpleDateFormat("dd/MM/yyyy").format(budget.getDate()));
        reportParams.put("PATIENT", Utils.extractPersonalData(budget.getPatientEntityDni().getUserEntityId()));
        reportParams.put("DOCTOR", "DENTALIUM S.L.");
        reportParams.put("STATUS", budget.getBudgetStatusEntityId().getName());

        final var pdfBudget = reportGenerator.generatePdfReport("budget", reportParams, dataConnectionProvider.getConnection());

        return signed ? signer.signDocument(pdfBudget, "Dental budget", Signer.Position.FINAL) : pdfBudget;
    }

    private byte[] getTeethImage(Set<Integer> teethList) throws Exception {
        try (final var inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("image/teeth_chart.svg")) {
            if (inputStream == null) {
                return new byte[0];
            }

            var svgTeethString = new String(inputStream.readAllBytes());
            svgTeethString = this.colourTeeth(svgTeethString, teethList);

            return svgTeethString.getBytes();
        }
    }

    private String colourTeeth(String svgTeethString, Set<Integer> teethList) {
        final var RED = "#FF0000";
        final var NONE = "none";

        for (Integer tooth : teethList) {
            String target = String.format("{tooth-%s}", tooth);
            svgTeethString = svgTeethString.replace(target, RED);
        }
        return svgTeethString.replaceAll("\\{tooth-\\d+}", NONE);
    }

    public Set<Integer> getImplicatedTeeth(BudgetEntity budgetEntity) {

        final var teethSet = new HashSet<String>();

        budgetEntity.getBudgetLineEntityList().forEach(
                v -> {
                    final var teeth = StringUtils.split(v.getTeeth(), ",");
                    teethSet.addAll(List.of(teeth));
                });
        return teethSet.stream().map(Integer::parseInt).collect(Collectors.toSet());
    }

}
