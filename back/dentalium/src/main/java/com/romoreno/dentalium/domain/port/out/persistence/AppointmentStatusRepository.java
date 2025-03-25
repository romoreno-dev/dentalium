package com.romoreno.dentalium.domain.port.out.persistence;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.AppointmentStatusEntity;

import java.util.Optional;

public interface AppointmentStatusRepository {
    Optional<AppointmentStatusEntity> find(Integer id);

}
