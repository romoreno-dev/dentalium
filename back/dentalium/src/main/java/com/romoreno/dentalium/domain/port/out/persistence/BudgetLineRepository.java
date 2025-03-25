package com.romoreno.dentalium.domain.port.out.persistence;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.BudgetLineEntity;

import java.util.List;

public interface BudgetLineRepository {

    //fixme Realmente para seguir arquitectura hexagonal deberian usarse aqui objetos del dominio y no entidades JPA (Ver TreatmentRepository)

    BudgetLineEntity save(BudgetLineEntity budgetLine);

    void delete(String budgetId, String treatment);

    void deleteEntity(BudgetLineEntity budgetLine);

    List<BudgetLineEntity> findAll(BudgetLineEntity budgetLine);


}
