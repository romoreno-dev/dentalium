package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchAppointmentRequest {

    private String patientId;
    private Integer doctorId;
    private Date since;
    private Date until;

}
