package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveAppointmentRequest {

    private Integer id;
    private String patientId;
    private String doctorId;
    private Date since;
    private Date until;
    private String observations;
    private Integer statusCode;

//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public class Status {
//        private String code;
//        private String description;
//    }

}
