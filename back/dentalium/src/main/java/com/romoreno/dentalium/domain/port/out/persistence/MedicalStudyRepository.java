package com.romoreno.dentalium.domain.port.out.persistence;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.MedicalStudyEntity;

import java.util.List;
import java.util.Optional;

public interface MedicalStudyRepository {

    //fixme Realmente para seguir arquitectura hexagonal deberian usarse aqui objetos del dominio y no entidades JPA (Ver TreatmentRepository)


    MedicalStudyEntity save(MedicalStudyEntity medicalStudy);

    void delete(Integer id);

    List<MedicalStudyEntity> findAll(MedicalStudyEntity medicalStudy);

    Optional<MedicalStudyEntity> findById(Integer id);

    List<MedicalStudyEntity> findAllByPatientId(String patientId);


}
