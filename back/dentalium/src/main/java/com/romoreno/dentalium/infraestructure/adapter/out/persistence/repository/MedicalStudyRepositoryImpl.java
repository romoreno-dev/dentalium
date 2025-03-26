package com.romoreno.dentalium.infraestructure.adapter.out.persistence.repository;

import com.romoreno.dentalium.domain.port.out.persistence.MedicalStudyRepository;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.MedicalStudyEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa.JpaMedicalStudyRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MedicalStudyRepositoryImpl implements MedicalStudyRepository {

    private final JpaMedicalStudyRepository jpaRepository;

    public MedicalStudyRepositoryImpl(JpaMedicalStudyRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public MedicalStudyEntity save(MedicalStudyEntity medicalStudy) {
        return jpaRepository.save(medicalStudy);
    }

    @Override
    public void delete(Integer id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<MedicalStudyEntity> findAll(MedicalStudyEntity medicalStudy) {
        return jpaRepository.findAll(Example.of(medicalStudy));
    }

    @Override
    public Optional<MedicalStudyEntity> findById(Integer id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<MedicalStudyEntity> findAllByPatientId(String patientId) {
        return jpaRepository.findAllMedicalStudiesByPatientId(patientId);
    }
}
