package com.romoreno.dentalium.domain.port.out.persistence;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.StudyTypeEntity;

import java.util.List;
import java.util.Optional;

public interface StudyTypeRepository {

    //fixme Realmente para seguir arquitectura hexagonal deberian usarse aqui objetos del dominio y no entidades JPA (Ver TreatmentRepository)

    StudyTypeEntity save(StudyTypeEntity studyType);

    void delete(Integer id);

    List<StudyTypeEntity> findAll(StudyTypeEntity studyType);

    Optional<StudyTypeEntity> find(Integer id);

    Optional<StudyTypeEntity> findByContentType(String contentType);

}
