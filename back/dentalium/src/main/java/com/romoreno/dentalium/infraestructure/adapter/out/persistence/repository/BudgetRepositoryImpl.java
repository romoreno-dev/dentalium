package com.romoreno.dentalium.infraestructure.adapter.out.persistence.repository;

import com.romoreno.dentalium.domain.port.out.persistence.BudgetRepository;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.BudgetEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa.JpaBudgetRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BudgetRepositoryImpl implements BudgetRepository {

    private final JpaBudgetRepository jpaRepository;

    public BudgetRepositoryImpl(JpaBudgetRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public BudgetEntity save(BudgetEntity budget) {
        return jpaRepository.save(budget);
    }

    @Override
    public void delete(String id) {
        jpaRepository.deleteById(Integer.parseInt(id));
    }

    @Override
    public List<BudgetEntity> findAll(BudgetEntity budget) {
        return jpaRepository.findAll(Example.of(budget));
    }

    @Override
    public List<BudgetEntity> findAllByPatientId(String patientId) {
        return jpaRepository.findAllBudgetsByPatientId(patientId);
    }

    @Override
    public Optional<BudgetEntity> findById(Integer id) {
        return jpaRepository.findById(id);
    }
}
