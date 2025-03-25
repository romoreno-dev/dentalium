package com.romoreno.dentalium.domain.port.in;

import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.assignment.dto.ListAssigmentResponse;

public interface AssignmentPort {

    boolean assignDoctor(String patientId, String doctorId);

    ListAssigmentResponse listAssigment(String patientId, String doctorId);


}
