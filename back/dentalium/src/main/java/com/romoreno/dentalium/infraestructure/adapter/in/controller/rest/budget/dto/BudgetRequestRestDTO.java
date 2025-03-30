package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.budget.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class BudgetRequestRestDTO {

    private Integer id;
    private String idPatient;
    private List<BudgetLineRestDTO> treatments;

    public BudgetRequestRestDTO() {
        treatments = new ArrayList<>();
    }
}
