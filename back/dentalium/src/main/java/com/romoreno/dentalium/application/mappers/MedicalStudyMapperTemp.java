package com.romoreno.dentalium.application.mappers;

import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.medical_study.dto.ListMedicalStudiesResponse;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.DoctorEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.MedicalStudyEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.PatientEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.StudyTypeEntity;
import com.romoreno.dentalium.infraestructure.config.annotations.MapperConversionTemp;

import java.util.Date;
import java.util.List;

@MapperConversionTemp
public class MedicalStudyMapperTemp {

    public MedicalStudyEntity toEntity(DoctorEntity doctorEntity, PatientEntity patientEntity, StudyTypeEntity studyTypeEntity,
                                       Date date, String path) {
        final var entity = new MedicalStudyEntity();
        entity.setDoctorEntityId(doctorEntity);
        entity.setPatientEntityId(patientEntity);
        entity.setStudyTypeEntityId(studyTypeEntity);
        entity.setDate(date);
        entity.setPath(path);
        return entity;
    }

    public ListMedicalStudiesResponse toAPI(List<MedicalStudyEntity> medicalStudyEntityList, String patientId) {
        final var listMedicalStudiesResponse = new ListMedicalStudiesResponse();
        listMedicalStudiesResponse.setPatientId(patientId);
        listMedicalStudiesResponse.setMedicalStudies(medicalStudyEntityList.stream().map(this::toAPI)
                .toList());
        return listMedicalStudiesResponse;
    }

    public ListMedicalStudiesResponse.MedicalStudy toAPI(MedicalStudyEntity medicalStudyEntity) {
        final var medicalStudy = new ListMedicalStudiesResponse.MedicalStudy();
        medicalStudy.setId(medicalStudyEntity.getId());
        medicalStudy.setStudyType(new ListMedicalStudiesResponse.StudyType(medicalStudyEntity.getStudyTypeEntityId().getId(),
                medicalStudyEntity.getStudyTypeEntityId().getName()));
        medicalStudy.setDate(medicalStudyEntity.getDate());
        medicalStudy.setDoctorId(medicalStudyEntity.getDoctorEntityId().getCollegeNumber());
        medicalStudy.setFilename(medicalStudyEntity.getPath().substring(medicalStudyEntity.getPath().lastIndexOf("_") + 1));
        return medicalStudy;
    }

}
