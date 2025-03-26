package com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.BudgetLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaBudgetLineRepository extends JpaRepository<BudgetLineEntity, Integer> {


    @Query("DELETE FROM BudgetLineEntity b WHERE b.budgetLinePKEntity.budgetId = :budgetId AND " +
            "b.budgetLinePKEntity.treatmentId = :treatmentId")
    @Modifying
    void delete(
            @Param("budgetId") String budgetId,
            @Param("treatmentId") String treatmentId
    );
}
