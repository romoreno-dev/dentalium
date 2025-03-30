package com.romoreno.dentalium.application.services;

import com.romoreno.dentalium.application.mappers.AppointmentMapperTemp;
import com.romoreno.dentalium.application.utils.Utils;
import com.romoreno.dentalium.domain.port.in.AppointmentPort;
import com.romoreno.dentalium.domain.port.out.persistence.AppointmentRepository;
import com.romoreno.dentalium.domain.port.out.persistence.AppointmentStatusRepository;
import com.romoreno.dentalium.domain.port.out.persistence.DataConnectionProvider;
import com.romoreno.dentalium.domain.port.out.report.ReportGenerator;
import com.romoreno.dentalium.domain.port.out.signer.Signer;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto.ModifyStatusAppointmentRequest;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto.SaveAppointmentRequest;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto.SearchAppointmentRequest;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto.SearchAppointmentResponse;
import com.romoreno.dentalium.infraestructure.config.annotations.GetTransaction;
import com.romoreno.dentalium.infraestructure.config.annotations.PortImplementation;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@PortImplementation
public class AppointmentService implements AppointmentPort {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentStatusRepository appointmentStatusRepository;
    private final AppointmentMapperTemp appointmentMapperTemp;
    private final DataConnectionProvider dataConnectionProvider;
    private final ReportGenerator reportGenerator;
    private final Signer signer;

    public AppointmentService(AppointmentRepository appointmentRepository, AppointmentStatusRepository appointmentStatusRepository,
                              AppointmentMapperTemp appointmentMapperTemp, DataConnectionProvider dataConnectionProvider,
                              ReportGenerator reportGenerator, Signer signer) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentStatusRepository = appointmentStatusRepository;
        this.appointmentMapperTemp = appointmentMapperTemp;
        this.dataConnectionProvider = dataConnectionProvider;
        this.reportGenerator = reportGenerator;
        this.signer = signer;
    }

    @Override
    public boolean saveAppointment(SaveAppointmentRequest appointment) throws Exception {
        final var appointmentStatusOpt = appointmentStatusRepository.find(appointment.getStatusCode());

        if (appointmentStatusOpt.isPresent()) {
            final var entity = appointmentMapperTemp.toEntity(appointment, appointmentStatusOpt.get());

            final var duplicatedEntity = appointmentRepository.findAll(entity.getAppointmentPKEntity());

            if (!duplicatedEntity.isEmpty() && (appointment.getId() == null || !appointment.getId().equals(entity.getId()))) {
                throw new Exception("Ya existe una cita agendada con estas características");
            }

            appointmentRepository.save(entity);
            return true;
        }
        return false;
    }

    @Override
    @GetTransaction
    public boolean modifyStatus(ModifyStatusAppointmentRequest request) throws Exception {
        final var appointmentStatusOpt = appointmentStatusRepository.find(request.getStatusCode());

        if (appointmentStatusOpt.isPresent()) {
            appointmentRepository.findById(request.getId())
                    .ifPresent(
                            v -> v.setAppointmentStatusEntityId(appointmentStatusOpt.get()));
            return true;
        }

        return false;
    }

    @Override
    public SearchAppointmentResponse getAppointments(SearchAppointmentRequest searchAppointmentRequest) {
        final var appointmentEntity = appointmentMapperTemp.toPKEntity(searchAppointmentRequest);
        final var appointmentList = appointmentRepository.findAll(appointmentEntity)
                .stream()
                .map(appointmentMapperTemp::toResponse)
                .toList();

        return new SearchAppointmentResponse(appointmentList);
    }

    @Override
    public boolean deleteAppointment(Integer id) {
        appointmentRepository.delete(id);
        return true;
    }

    @Override
    public byte[] generatePdfCertificate(int id, boolean signed) throws Exception {

        final var appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isEmpty()) {
            return null;
        }
        final var appointment = appointmentOpt.get();

        if (appointment.getAppointmentStatusEntityId().getId() == 4) {
            return null;
        }

        final var message = new StringBuilder("La clínica dental DENTALIUM, S.L. certifica que ")
                .append(Utils.extractPersonalData(appointment.getPatientEntity().getUserEntityId()))
                .append(" ha ").append(appointment.getAppointmentStatusEntityId().getName())
                .append(" TRATAMIENTO ODONTOLÓGICO el ")
                .append(new SimpleDateFormat(" dd 'de' MMMM 'de' yyyy 'en torno a las' hh:mm a")
                        .format(appointment.getAppointmentPKEntity().getInitDate()));

        final var reportParams = new HashMap<String, Object>();
        reportParams.put("DATE", new SimpleDateFormat("'En Málaga a,' dd 'de' MMMM 'de' yyyy").format(new Date()));
        reportParams.put("CERTIFICATE_TEXT", message.toString());

        final var pdfBudget = reportGenerator.generatePdfReport("certificate", reportParams, null);

        return signed ? signer.signDocument(pdfBudget, "Certificate", Signer.Position.CENTRAL) : pdfBudget;    }
}
