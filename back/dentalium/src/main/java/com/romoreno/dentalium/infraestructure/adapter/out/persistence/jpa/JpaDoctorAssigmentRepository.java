package com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.DoctorAssigmentEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.DoctorAssigmentPKEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaDoctorAssigmentRepository extends JpaRepository<DoctorAssigmentEntity, DoctorAssigmentPKEntity> {

    @Query("SELECT d FROM DoctorAssigmentEntity d WHERE d.doctorAssigmentPKEntity.idPatient = :patientId AND d.to IS NULL")
    Optional<DoctorAssigmentEntity> searchAssigment(@Param("patientId") String patientId);

    @Query("SELECT d FROM DoctorAssigmentEntity d " +
            "WHERE (:patientId IS NULL OR d.doctorAssigmentPKEntity.idPatient = :patientId) " +
            "AND (:doctorId IS NULL OR d.doctorAssigmentPKEntity.idDoctor = :doctorId) " +
            "AND (d.to IS NULL) ORDER BY d.doctorAssigmentPKEntity.since DESC ")
    List<DoctorAssigmentEntity> findActiveAssigments(
            @Param("patientId") String patientId,
            @Param("doctorId") String doctorId
    );


}
