package com.romoreno.dentalium.application.mappers;

import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto.SaveAppointmentRequest;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto.SearchAppointmentRequest;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto.SearchAppointmentResponse;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.AppointmentEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.AppointmentPKEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.AppointmentStatusEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.romoreno.dentalium.infraestructure.config.annotations.MapperConversionTemp;
import org.apache.commons.lang3.StringUtils;

@MapperConversionTemp
public class AppointmentMapperTemp {

    public AppointmentEntity toEntity(SaveAppointmentRequest saveAppointmentRequest, AppointmentStatusEntity appointmentStatusEntity) {
        final var appointmentEntity = new AppointmentEntity();

        final var appointmentPKEntity = toPKEntity(saveAppointmentRequest);

        appointmentEntity.setId(saveAppointmentRequest.getId());
        appointmentEntity.setAppointmentPKEntity(appointmentPKEntity);
        appointmentEntity.setAppointmentStatusEntityId(appointmentStatusEntity);
        appointmentEntity.setObservations(saveAppointmentRequest.getObservations());
        return appointmentEntity;
    }

    public AppointmentPKEntity toPKEntity(SearchAppointmentRequest searchAppointmentRequest) {
        final var appointmentPKEntity = new AppointmentPKEntity();

        appointmentPKEntity.setDoctorId(searchAppointmentRequest.getDoctorId());
        appointmentPKEntity.setInitDate(searchAppointmentRequest.getSince());
        appointmentPKEntity.setEndDate(searchAppointmentRequest.getUntil());
        appointmentPKEntity.setPatientId(searchAppointmentRequest.getPatientId());
        return appointmentPKEntity;
    }

    public AppointmentPKEntity toPKEntity(SaveAppointmentRequest saveAppointmentRequest) {
        final var appointmentPKEntity = new AppointmentPKEntity();

        appointmentPKEntity.setDoctorId(Integer.parseInt(saveAppointmentRequest.getDoctorId()));
        appointmentPKEntity.setInitDate(saveAppointmentRequest.getSince());
        appointmentPKEntity.setEndDate(saveAppointmentRequest.getUntil());
        appointmentPKEntity.setPatientId(String.valueOf(saveAppointmentRequest.getPatientId()));
        return appointmentPKEntity;
    }

    public SearchAppointmentResponse.Appointment toResponse(AppointmentEntity entity) {
        final var appointment = new SearchAppointmentResponse.Appointment();
        appointment.setId(entity.getId());
        appointment.setDoctor(new SearchAppointmentResponse.Person(entity.getAppointmentPKEntity().getDoctorId().toString(),
                this.extractPersonalData(entity.getDoctorEntity().getUserEntityId())));
        appointment.setPatient(new SearchAppointmentResponse.Person(entity.getAppointmentPKEntity().getPatientId(),
                this.extractPersonalData(entity.getPatientEntity().getUserEntityId())));
        appointment.setInitDate(entity.getAppointmentPKEntity().getInitDate());
        appointment.setEndDate(entity.getAppointmentPKEntity().getEndDate());
        appointment.setObservations(entity.getObservations());
        appointment.setStatus(this.convertStatus(entity.getAppointmentStatusEntityId()));
        return appointment;
    }

    private String extractPersonalData(UserEntity user) {
        return String.format("%s %s, %s",
                StringUtils.defaultIfBlank(user.getSurname1(), "").toUpperCase(),
                StringUtils.defaultIfBlank(user.getSurname2(), "").toUpperCase(),
                StringUtils.defaultIfBlank(user.getName(), "").toUpperCase());
    }

    private SearchAppointmentResponse.Status convertStatus(AppointmentStatusEntity statusEntity) {
        return new SearchAppointmentResponse.Status(statusEntity.getId().toString(), statusEntity.getName());
    }

}
