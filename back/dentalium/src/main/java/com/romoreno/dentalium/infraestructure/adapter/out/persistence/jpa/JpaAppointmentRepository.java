package com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface JpaAppointmentRepository extends JpaRepository<AppointmentEntity, Integer> {

    @Query("SELECT a FROM AppointmentEntity a " +
            "WHERE (:patientId IS NULL OR a.appointmentPKEntity.patientId = :patientId) " +
            "AND (:doctorId IS NULL OR a.appointmentPKEntity.doctorId = :doctorId) " +
            "AND (:since IS NULL OR a.appointmentPKEntity.initDate >= :since) " +
            "AND (:until IS NULL OR a.appointmentPKEntity.endDate <= :until) ORDER BY a.appointmentPKEntity.initDate")
    List<AppointmentEntity> findAppointments(
            @Param("patientId") String patientId,
            @Param("doctorId") Integer doctorId,
            @Param("since") Date since,
            @Param("until") Date until
    );

}
