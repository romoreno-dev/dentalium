package com.romoreno.dentalium.infraestructure.adapter.out.persistence.repository;

import com.romoreno.dentalium.domain.port.out.persistence.AppointmentRepository;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.AppointmentEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.AppointmentPKEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa.JpaAppointmentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepository {

    private final JpaAppointmentRepository jpaRepository;

    public AppointmentRepositoryImpl(JpaAppointmentRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public AppointmentEntity save(AppointmentEntity appointment) {
        return jpaRepository.save(appointment);
    }

    @Override
    public void delete(Integer id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<AppointmentEntity> findAll(AppointmentPKEntity appointmentPKEntity) {
        return jpaRepository.findAppointments(appointmentPKEntity.getPatientId(),
                appointmentPKEntity.getDoctorId(),
                appointmentPKEntity.getInitDate(),
                appointmentPKEntity.getEndDate());
//        final var matcher = ExampleMatcher.matching()
//                .withIgnoreNullValues();
//        return jpaRepository.findAll(Example.of(appointment, matcher));
    }

    @Override
    public Optional<AppointmentEntity> findById(Integer id) {
        return jpaRepository.findById(id);
    }
}
