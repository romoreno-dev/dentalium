package com.romoreno.dentalium.domain.port.out.persistence;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.AppointmentEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.AppointmentPKEntity;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {

    //fixme Realmente para seguir arquitectura hexagonal deberian usarse aqui objetos del dominio y no entidades JPA (Ver TreatmentRepository)
    AppointmentEntity save(AppointmentEntity appointment);

    void delete(Integer id);

    List<AppointmentEntity> findAll(AppointmentPKEntity appointment);

    Optional<AppointmentEntity> findById(Integer id);

}
