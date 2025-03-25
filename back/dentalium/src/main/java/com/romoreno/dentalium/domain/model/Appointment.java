package com.romoreno.dentalium.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    private String patientId;
    private String doctorId;
    private Date since;
    private Date until;
}
