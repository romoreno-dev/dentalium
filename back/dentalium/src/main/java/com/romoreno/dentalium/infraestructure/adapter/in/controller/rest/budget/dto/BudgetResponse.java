package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.budget.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class BudgetResponse {

    private String patientDni;
    private List<Budget> budgets;

    public BudgetResponse() {
        budgets = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Budget {
        private Integer id;
        private Date date;
        private Status status;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status {
        private Integer id;
        private String description;
    }

}
