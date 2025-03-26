package com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.BudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaBudgetRepository extends JpaRepository<BudgetEntity, Integer> {

    @Query("SELECT b FROM BudgetEntity b WHERE b.patientEntityDni.dni = :patientId")
    List<BudgetEntity> findAllBudgetsByPatientId(@Param("patientId") String patientId);

}
