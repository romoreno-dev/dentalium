package com.romoreno.dentalium.domain.port.out.persistence;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.BudgetEntity;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository {

    //fixme Realmente para seguir arquitectura hexagonal deberian usarse aqui objetos del dominio y no entidades JPA (Ver TreatmentRepository)

    BudgetEntity save(BudgetEntity budget);

    void delete(String id);

    List<BudgetEntity> findAll(BudgetEntity budget);

    List<BudgetEntity> findAllByPatientId(String patientId);

    Optional<BudgetEntity> findById(Integer id);

}
