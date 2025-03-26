package com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.MedicalStudyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaMedicalStudyRepository extends JpaRepository<MedicalStudyEntity, Integer> {

    @Query("SELECT m FROM MedicalStudyEntity m WHERE m.patientEntityId.dni = :patientId")
    List<MedicalStudyEntity> findAllMedicalStudiesByPatientId(String patientId);

}
