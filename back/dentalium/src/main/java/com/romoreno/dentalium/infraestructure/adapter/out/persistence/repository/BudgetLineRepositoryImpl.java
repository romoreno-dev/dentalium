package com.romoreno.dentalium.infraestructure.adapter.out.persistence.repository;

import com.romoreno.dentalium.domain.port.out.persistence.BudgetLineRepository;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.BudgetLineEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa.JpaBudgetLineRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BudgetLineRepositoryImpl implements BudgetLineRepository {

    private final JpaBudgetLineRepository jpaRepository;

    public BudgetLineRepositoryImpl(JpaBudgetLineRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public BudgetLineEntity save(BudgetLineEntity budgetLine) {
        return jpaRepository.save(budgetLine);
    }

    @Override
    public void delete(String budgetId, String treatment) {
        jpaRepository.delete(budgetId, treatment);
    }

    @Override
    public void deleteEntity(BudgetLineEntity budgetLine) {
        jpaRepository.delete(budgetLine);
    }

    @Override
    public List<BudgetLineEntity> findAll(BudgetLineEntity budgetLine) {
        return jpaRepository.findAll(Example.of(budgetLine));
    }

}
