package com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.AppointmentStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAppointmentStatusRepository extends JpaRepository<AppointmentStatusEntity, Integer> {
}
