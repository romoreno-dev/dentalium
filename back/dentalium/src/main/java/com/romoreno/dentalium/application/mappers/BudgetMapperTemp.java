package com.romoreno.dentalium.application.mappers;

import com.romoreno.dentalium.application.utils.Utils;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.budget.dto.BudgetLineRestDTO;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.budget.dto.BudgetRequestRestDTO;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.budget.dto.BudgetResponse;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.budget.dto.BudgetWithBudgetLinesResponse;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.*;
import com.romoreno.dentalium.infraestructure.config.annotations.MapperConversionTemp;

import java.util.Date;
import java.util.List;

@MapperConversionTemp
public class BudgetMapperTemp {

    public BudgetEntity toEntity(BudgetRequestRestDTO budget, BudgetStatusEntity budgetStatusEntity,
                                 PatientEntity patientEntity) {
        final var budgetEntity = new BudgetEntity();
        budgetEntity.setId(budget.getId());
        budgetEntity.setDate(new Date());
        budgetEntity.setBudgetStatusEntityId(budgetStatusEntity);
        budgetEntity.setPatientEntityDni(patientEntity);
        return budgetEntity;
    }

    public BudgetLineEntity toEntity(BudgetLineRestDTO budget, BudgetEntity budgetEntity) {

        final var budgetLinePKEntity = new BudgetLinePKEntity();
        budgetLinePKEntity.setTreatmentId(budget.getTreatmentId());
        budgetLinePKEntity.setBudgetId(budgetEntity.getId());

        final var budgetLineEntity = new BudgetLineEntity();
        budgetLineEntity.setBudgetEntity(budgetEntity);
        budgetLineEntity.setBudgetLinePKEntity(budgetLinePKEntity);
        budgetLineEntity.setQuantity(budget.getQuantity());
        budgetLineEntity.setTeeth(budget.getTeeth());
        budgetLineEntity.setDiscount(budget.getDiscount());

        return budgetLineEntity;
    }

    public BudgetLineEntity toEntity(Integer id, BudgetLineRestDTO budgetLine) {
        final var budgetLineEntity = new BudgetLineEntity();

        final var budgetLinePKEntity = toPKEntity(id, budgetLine.getTreatmentId());
        budgetLineEntity.setBudgetLinePKEntity(budgetLinePKEntity);
        budgetLineEntity.setQuantity(budgetLine.getQuantity());
        budgetLineEntity.setTeeth(budgetLine.getTeeth());
        budgetLineEntity.setDiscount(budgetLine.getDiscount());

        return budgetLineEntity;
    }

    public BudgetLinePKEntity toPKEntity(Integer budgetId, Integer treatmentId) {
        final var budgetLinePKEntity = new BudgetLinePKEntity();
        budgetLinePKEntity.setBudgetId(budgetId);
        budgetLinePKEntity.setTreatmentId(treatmentId);
        return budgetLinePKEntity;
    }

    public BudgetResponse toAPI(String patientId, List<BudgetEntity> budgetEntity) {

        final var budgets = budgetEntity.stream()
                .map(this::toAPI)
                .toList();

        final var budgetResponse = new BudgetResponse();
        budgetResponse.setPatientDni(patientId);
        budgetResponse.setBudgets(budgets);
        return budgetResponse;
    }

    private BudgetResponse.Budget toAPI(BudgetEntity budgetEntity) {
        final var budget = new BudgetResponse.Budget();
        budget.setId(budgetEntity.getId());
        budget.setDate(budgetEntity.getDate());
        budget.setStatus(new BudgetResponse.Status(budgetEntity.getBudgetStatusEntityId().getId(),
                budgetEntity.getBudgetStatusEntityId().getName()));

        return budget;
    }

    public BudgetWithBudgetLinesResponse toAPIWithBudgetLines(BudgetEntity budgetEntity) {
        final var budgetWithBudgetLines = new BudgetWithBudgetLinesResponse();
        budgetWithBudgetLines.setId(budgetEntity.getId());
        budgetWithBudgetLines.setPatientId(budgetEntity.getPatientEntityDni().getDni());
        budgetWithBudgetLines.setPatientName(Utils.extractPersonalData(budgetEntity.getPatientEntityDni().getUserEntityId()));
        budgetWithBudgetLines.setDate(budgetEntity.getDate());
        budgetWithBudgetLines.setStatus(new BudgetWithBudgetLinesResponse.Status(budgetEntity.getBudgetStatusEntityId().getId(),
                budgetEntity.getBudgetStatusEntityId().getName()));
        budgetWithBudgetLines.setBudgetLine(budgetEntity.getBudgetLineEntityList().stream()
                .map(this::toAPI).toList());
        budgetWithBudgetLines.setTotalPrice(String.format("%.2f €", budgetWithBudgetLines.getBudgetLine()
                .stream()
                .mapToDouble(line -> Double.parseDouble(line.getTotalPrice().replace(",",".").replace(" €", "")))
                .sum()));
        return budgetWithBudgetLines;
    }

    private BudgetWithBudgetLinesResponse.BudgetLine toAPI(BudgetLineEntity budgetLineEntity) {
        final var budgetLineRestDTO = new BudgetWithBudgetLinesResponse.BudgetLine();
        budgetLineRestDTO.setQuantity(budgetLineEntity.getQuantity());
        budgetLineRestDTO.setDiscount(String.format("%.0f %%", budgetLineEntity.getDiscount()));
        budgetLineRestDTO.setUnitPrice(String.format("%.2f €", budgetLineEntity.getTreatmentEntity().getUnitPrice()));
        budgetLineRestDTO.setTreatmentPrice(String.format("%.2f €", budgetLineEntity.getQuantity()*budgetLineEntity.getTreatmentEntity().getUnitPrice()));
        budgetLineRestDTO.setTotalPrice(String.format("%.2f €",
                budgetLineEntity.getQuantity()*budgetLineEntity.getTreatmentEntity().getUnitPrice()*((100-budgetLineEntity.getDiscount())/100)));
        budgetLineRestDTO.setTeeth(budgetLineEntity.getTeeth());
        budgetLineRestDTO.setTreatmentId(budgetLineEntity.getTreatmentEntity().getId());
        budgetLineRestDTO.setTreatmentName(budgetLineEntity.getTreatmentEntity().getName());
        return budgetLineRestDTO;
    }

}
