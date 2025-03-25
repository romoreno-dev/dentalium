package com.romoreno.dentalium.domain.port.out.persistence;


import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.DoctorEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.PatientEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.RoleEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    //fixme Realmente para seguir arquitectura hexagonal deberian usarse aqui objetos del dominio y no entidades JPA (Ver TreatmentRepository)

    UserEntity save(UserEntity userEntity, PatientEntity patientEntity);
    UserEntity save(UserEntity userEntity, DoctorEntity doctorEntity);
    UserEntity save(UserEntity userEntity);

    Optional<RoleEntity> getRole(String role);

    boolean delete(Long id);
    List<UserEntity> findAll(UserEntity UserEntity);
    Optional<UserEntity> findById(Long id);

    List<PatientEntity> findAllPatients();
    List<DoctorEntity> findAllDoctors();

    Optional<PatientEntity> findPatient(String patientId);

    Optional<DoctorEntity> findDoctor(String doctorId);

    Optional<PatientEntity> findPatientByUsername(String patientUsername);

    Optional<DoctorEntity> findDoctorByUsername(String doctorUsername);
}
