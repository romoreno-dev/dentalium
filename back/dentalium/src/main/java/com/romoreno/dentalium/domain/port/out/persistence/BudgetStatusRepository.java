package com.romoreno.dentalium.domain.port.out.persistence;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.BudgetStatusEntity;

import java.util.Optional;

public interface BudgetStatusRepository {

    Optional<BudgetStatusEntity> find(Integer id);

}
