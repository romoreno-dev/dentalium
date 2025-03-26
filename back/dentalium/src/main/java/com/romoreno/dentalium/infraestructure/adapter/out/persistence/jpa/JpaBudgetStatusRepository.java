package com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.BudgetStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBudgetStatusRepository extends JpaRepository<BudgetStatusEntity, Integer> {
}
