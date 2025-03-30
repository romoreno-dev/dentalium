package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.budget.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetLineRestDTO {
    private Integer treatmentId;
    private Integer quantity;
    private String teeth;
    private Float discount;
}
