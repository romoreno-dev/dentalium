package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyStatusAppointmentRequest {
    private Integer id;
    private Integer statusCode;
}
