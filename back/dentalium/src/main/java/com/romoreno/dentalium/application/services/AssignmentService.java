package com.romoreno.dentalium.application.services;

import com.romoreno.dentalium.application.mappers.AssignmentMapperTemp;
import com.romoreno.dentalium.domain.port.in.AssignmentPort;
import com.romoreno.dentalium.domain.port.out.persistence.DoctorAssigmentRepository;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.assignment.dto.ListAssigmentResponse;
import com.romoreno.dentalium.infraestructure.config.annotations.GetTransaction;
import com.romoreno.dentalium.infraestructure.config.annotations.PortImplementation;
import io.micrometer.common.util.StringUtils;

import java.util.Date;

@PortImplementation
public class AssignmentService implements AssignmentPort {

    private final DoctorAssigmentRepository assignmentRepository;
    private final AssignmentMapperTemp assignmentMapperTemp;

    public AssignmentService(DoctorAssigmentRepository assignmentRepository, AssignmentMapperTemp assignmentMapperTemp) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapperTemp = assignmentMapperTemp;
    }

    @Override
    @GetTransaction
    public boolean assignDoctor(String patientId, String doctorId) {
        assignmentRepository.finishAssigment(patientId);
        assignmentRepository.save(assignmentMapperTemp.toEntity(patientId, doctorId, new Date()));
        return true;
    }

    @Override
    public ListAssigmentResponse listAssigment(String patientId, String doctorId) {
        if (StringUtils.isNotBlank(patientId)) {
            return new ListAssigmentResponse(assignmentRepository.findDoctorsActiveAssigment(patientId)
                    .stream()
                    .map(assignmentMapperTemp::toResponseWithDoctors)
                    .toList());
        } else {
            return new ListAssigmentResponse(assignmentRepository.findPatientsActiveAssigment(doctorId)
                    .stream()
                    .map(assignmentMapperTemp::toResponseWithPatients)
                    .toList());
        }
    }
}
