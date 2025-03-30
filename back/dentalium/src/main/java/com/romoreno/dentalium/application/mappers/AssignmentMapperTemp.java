package com.romoreno.dentalium.application.mappers;

import com.romoreno.dentalium.application.utils.Utils;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.assignment.dto.ListAssigmentResponse;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.DoctorAssigmentEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.DoctorAssigmentPKEntity;
import com.romoreno.dentalium.infraestructure.config.annotations.MapperConversionTemp;

import java.util.Date;

@MapperConversionTemp
public class AssignmentMapperTemp {

    public DoctorAssigmentEntity toEntity(String idPatient, String idDoctor, Date date) {
        final var doctorAssigmentEntity = new DoctorAssigmentEntity();
        doctorAssigmentEntity.setDoctorAssigmentPKEntity(toPKEntity(idPatient, idDoctor, date));
        return doctorAssigmentEntity;
    }

    public DoctorAssigmentPKEntity toPKEntity(String idPatient, String idDoctor, Date date) {
        final var doctorAssigmentPKEntity = new DoctorAssigmentPKEntity();
        doctorAssigmentPKEntity.setIdDoctor(idDoctor);
        doctorAssigmentPKEntity.setIdPatient(idPatient);
        doctorAssigmentPKEntity.setSince(date);
        return doctorAssigmentPKEntity;
    }

    public ListAssigmentResponse.Person toResponseWithDoctors(DoctorAssigmentEntity entity) {
        return new ListAssigmentResponse.Person(entity.getDoctorAssigmentPKEntity().getIdDoctor(),
                Utils.extractPersonalData(entity.getDoctorEntity().getUserEntityId()), entity.getDoctorAssigmentPKEntity().getSince());
    }

    public ListAssigmentResponse.Person toResponseWithPatients(DoctorAssigmentEntity entity) {
        return new ListAssigmentResponse.Person(entity.getDoctorAssigmentPKEntity().getIdPatient(),
                Utils.extractPersonalData(entity.getPatientEntity().getUserEntityId()), entity.getDoctorAssigmentPKEntity().getSince());
    }

}
