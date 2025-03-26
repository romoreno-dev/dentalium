package com.romoreno.dentalium.infraestructure.adapter.out.persistence.repository;

import com.romoreno.dentalium.domain.port.out.persistence.UserRepository;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.DoctorEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.PatientEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.RoleEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa.JpaDoctorRepository;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa.JpaPatientRepository;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa.JpaRoleRepository;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa.JpaUserRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final JpaPatientRepository jpaPatientRepository;
    private final JpaDoctorRepository jpaDoctorRepository;
    private final JpaRoleRepository jpaRoleRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository, JpaPatientRepository jpaPatientRepository, JpaDoctorRepository jpaDoctorRepository, JpaRoleRepository jpaRoleRepository) {
        this.jpaUserRepository = jpaUserRepository;
        this.jpaPatientRepository = jpaPatientRepository;
        this.jpaDoctorRepository = jpaDoctorRepository;
        this.jpaRoleRepository = jpaRoleRepository;
    }

    @Override
    public UserEntity save(UserEntity userEntity, PatientEntity patientEntity) {
        final var user = jpaUserRepository.save(userEntity);
        patientEntity.setUserEntityId(user);
        user.setPatientEntityList(List.of(jpaPatientRepository.save(patientEntity)));
        return user;
    }

    @Override
    public Optional<RoleEntity> getRole(String role) {
        return jpaRoleRepository.getRole(role);
    }

    @Override
    public UserEntity save(UserEntity userEntity, DoctorEntity doctorEntity) {
        final var user = jpaUserRepository.save(userEntity);
        doctorEntity.setUserEntityId(user);
        user.setDoctorEntityList(List.of(jpaDoctorRepository.save(doctorEntity)));
        return user;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return jpaUserRepository.save(userEntity);
    }

    @Override
    public boolean delete(Long id) {
        jpaUserRepository.delete(findById(id).get());
        return true;
    }

    @Override
    public List<UserEntity> findAll(UserEntity userEntity) {
        return jpaUserRepository.findAll(Example.of(userEntity));
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public List<PatientEntity> findAllPatients() {
        return jpaUserRepository.findAllPatients();
    }

    @Override
    public List<DoctorEntity> findAllDoctors() {
        return jpaUserRepository.findAllDoctors();
    }

    @Override
    public Optional<PatientEntity> findPatient(String patientId) {
        return jpaUserRepository.findPatient(patientId);
    }

    @Override
    public Optional<DoctorEntity> findDoctor(String doctorId) {
        return jpaUserRepository.findDoctor(doctorId);
    }

    @Override
    public Optional<PatientEntity> findPatientByUsername(String patientUsername) {
        return jpaUserRepository.findPatientByUsername(patientUsername);
    }

    @Override
    public Optional<DoctorEntity> findDoctorByUsername(String doctorUsername) {
        return jpaUserRepository.findDoctorByUsername(doctorUsername);
    }

}
