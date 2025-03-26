package com.romoreno.dentalium.infraestructure.adapter.out.persistence.repository;

import com.romoreno.dentalium.domain.port.out.persistence.StudyTypeRepository;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.StudyTypeEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa.JpaStudyTypeRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudyTypeRepositoryImpl implements StudyTypeRepository {

    private final JpaStudyTypeRepository jpaRepository;

    public StudyTypeRepositoryImpl(JpaStudyTypeRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public StudyTypeEntity save(StudyTypeEntity studyType) {
        return jpaRepository.save(studyType);
    }

    @Override
    public void delete(Integer id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<StudyTypeEntity> findAll(StudyTypeEntity studyType) {
        return jpaRepository.findAll(Example.of(studyType));
    }

    @Override
    public Optional<StudyTypeEntity> find(Integer id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<StudyTypeEntity> findByContentType(String contentType) {
        return jpaRepository.findByContentType(contentType);
    }
}
