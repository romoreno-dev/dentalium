package com.romoreno.dentalium.domain.port.out.persistence;


import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.DoctorAssigmentEntity;

import java.util.List;

public interface DoctorAssigmentRepository {

    //fixme Realmente para seguir arquitectura hexagonal deberian usarse aqui objetos del dominio y no entidades JPA (Ver TreatmentRepository)

    DoctorAssigmentEntity save(DoctorAssigmentEntity doctorAssigment);

    void delete(Integer id);

    List<DoctorAssigmentEntity> findAll(DoctorAssigmentEntity doctorAssigment);

    boolean finishAssigment(String idPatient);

    List<DoctorAssigmentEntity> findPatientsActiveAssigment(String doctorId);

    List<DoctorAssigmentEntity> findDoctorsActiveAssigment(String patientId);

}
