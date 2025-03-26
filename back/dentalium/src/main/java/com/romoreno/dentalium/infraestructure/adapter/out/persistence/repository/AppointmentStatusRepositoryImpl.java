package com.romoreno.dentalium.infraestructure.adapter.out.persistence.repository;

import com.romoreno.dentalium.domain.port.out.persistence.AppointmentStatusRepository;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.AppointmentStatusEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa.JpaAppointmentStatusRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AppointmentStatusRepositoryImpl implements AppointmentStatusRepository {

    private final JpaAppointmentStatusRepository jpaAppointmentStatusRepository;

    public AppointmentStatusRepositoryImpl(JpaAppointmentStatusRepository jpaAppointmentStatusRepository) {
        this.jpaAppointmentStatusRepository = jpaAppointmentStatusRepository;
    }

    @Override
    public Optional<AppointmentStatusEntity> find(Integer id) {
        return jpaAppointmentStatusRepository.findById(id);
    }
}
