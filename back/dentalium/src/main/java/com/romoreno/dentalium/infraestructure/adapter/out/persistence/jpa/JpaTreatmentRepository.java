package com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.TreatmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTreatmentRepository extends JpaRepository<TreatmentEntity, Integer> {
}
