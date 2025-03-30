package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignDoctorRequest {

    private String patientId;
    private String doctorId;

}
