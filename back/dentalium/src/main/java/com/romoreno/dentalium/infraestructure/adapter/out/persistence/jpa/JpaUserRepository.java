package com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.DoctorEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.PatientEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT p FROM UserEntity u, PatientEntity p WHERE u.id = p.userEntityId.id AND p.dni = :patientId AND u.valid = '1'")
    Optional<PatientEntity> findPatient(@Param("patientId") String patientId);

    @Query("SELECT d FROM UserEntity u, DoctorEntity d WHERE u.id = d.userEntityId.id AND d.collegeNumber = :doctorId AND u.valid = '1'")
    Optional<DoctorEntity> findDoctor(@Param("doctorId") String doctorId);

    @Query("SELECT p FROM PatientEntity p, UserEntity u WHERE u.id = p.userEntityId.id AND u.valid = '1' ")
    List<PatientEntity> findAllPatients();

    @Query("SELECT d FROM DoctorEntity d, UserEntity u WHERE u.id = d.userEntityId.id AND u.valid = '1'")
    List<DoctorEntity> findAllDoctors();

    @Query("SELECT p FROM UserEntity u, PatientEntity p WHERE u.id = p.userEntityId.id AND u.user = :patientUsername AND u.valid = '1'")
    Optional<PatientEntity> findPatientByUsername(@Param("patientUsername") String patientUsername);

    @Query("SELECT d FROM UserEntity u, DoctorEntity d WHERE u.id = d.userEntityId.id AND u.user = :doctorUsername AND u.valid = '1'")
    Optional<DoctorEntity> findDoctorByUsername(@Param("doctorUsername") String doctorUsername);


}
