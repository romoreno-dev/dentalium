package com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.StudyTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaStudyTypeRepository extends JpaRepository<StudyTypeEntity, Integer> {

    @Query("SELECT s FROM StudyTypeEntity s WHERE s.contentType = :contentType")
    Optional<StudyTypeEntity> findByContentType(@Param("contentType") String contentType);
}
