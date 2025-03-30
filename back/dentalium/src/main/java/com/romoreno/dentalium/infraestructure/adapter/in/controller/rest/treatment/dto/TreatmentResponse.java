package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.treatment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class TreatmentResponse {

    private List<TreatmentRestDTO> treatments;

    public TreatmentResponse() {
        treatments = new ArrayList<>();
    }

}
