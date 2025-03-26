package com.romoreno.dentalium.infraestructure.adapter.out.persistence.repository;

import com.romoreno.dentalium.domain.model.Treatment;
import com.romoreno.dentalium.domain.port.out.persistence.TreatmentRepository;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa.JpaTreatmentRepository;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.mapper.TreatmentEntityMapper;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TreatmentRepositoryImpl implements TreatmentRepository {

    private final JpaTreatmentRepository jpaRepository;
    private final TreatmentEntityMapper treatmentEntityMapper;

    public TreatmentRepositoryImpl(JpaTreatmentRepository jpaRepository, TreatmentEntityMapper treatmentEntityMapper) {
        this.jpaRepository = jpaRepository;
        this.treatmentEntityMapper = treatmentEntityMapper;
    }

    @Override
    public Treatment save(Treatment treatment) {
        final var entity = treatmentEntityMapper.toEntity(treatment);
        return treatmentEntityMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Treatment> findAll(Treatment treatment) {
        final var needle = treatmentEntityMapper.toEntity(treatment);
        return jpaRepository.findAll(Example.of(needle))
                .stream().map(treatmentEntityMapper::toDomain)
                .toList();
    }

}
