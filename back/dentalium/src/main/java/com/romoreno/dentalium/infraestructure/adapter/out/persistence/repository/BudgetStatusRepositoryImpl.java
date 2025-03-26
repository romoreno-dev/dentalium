package com.romoreno.dentalium.infraestructure.adapter.out.persistence.repository;

import com.romoreno.dentalium.domain.port.out.persistence.BudgetStatusRepository;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.BudgetStatusEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa.JpaBudgetStatusRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BudgetStatusRepositoryImpl implements BudgetStatusRepository {

    private final JpaBudgetStatusRepository jpaRepository;

    public BudgetStatusRepositoryImpl(JpaBudgetStatusRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<BudgetStatusEntity> find(Integer id) {
        return jpaRepository.findById(id);
    }

}
