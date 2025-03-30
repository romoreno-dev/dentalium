package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class SearchAppointmentResponse {

    private List<Appointment> appointmentList;

    public SearchAppointmentResponse() {
        appointmentList = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Appointment {
        private Integer id;
        private Date initDate;
        private Date endDate;
        private String observations;
        private Status status;
        private Person doctor;
        private Person patient;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Person {
        private String id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status {
        private String id;
        private String description;
    }

}
