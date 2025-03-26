package com.romoreno.dentalium.infraestructure.adapter.out.persistence.repository;

import com.romoreno.dentalium.domain.port.out.persistence.DoctorAssigmentRepository;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.DoctorAssigmentEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa.JpaDoctorAssigmentRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class DoctorAssigmentRepositoryImpl implements DoctorAssigmentRepository {

    private final JpaDoctorAssigmentRepository jpaRepository;

    public DoctorAssigmentRepositoryImpl(JpaDoctorAssigmentRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public DoctorAssigmentEntity save(DoctorAssigmentEntity doctorAssigment) {
        return jpaRepository.save(doctorAssigment);
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("No implementado");
        //jpaRepository.deleteById(id);
    }

    @Override
    public List<DoctorAssigmentEntity> findAll(DoctorAssigmentEntity doctorAssigment) {
        return jpaRepository.findAll(Example.of(doctorAssigment));
    }

    @Override
    public boolean finishAssigment(String idPatient) {
        jpaRepository.searchAssigment(idPatient).ifPresent(
                v -> {
                    v.setTo(new Date());
                    jpaRepository.save(v);
                });
        return true;
    }

    @Override
    public List<DoctorAssigmentEntity> findPatientsActiveAssigment(String doctorId) {
        return jpaRepository.findActiveAssigments(null, doctorId);
    }

    @Override
    public List<DoctorAssigmentEntity> findDoctorsActiveAssigment(String patientId) {
        return jpaRepository.findActiveAssigments(patientId, null);
    }
}
