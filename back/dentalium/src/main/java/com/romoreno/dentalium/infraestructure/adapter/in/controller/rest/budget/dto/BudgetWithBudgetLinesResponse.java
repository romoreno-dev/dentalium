package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.budget.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class BudgetWithBudgetLinesResponse {

    private Integer id;
    private String patientId;
    private String patientName;
    private Date date;
    private Status status;
    private String totalPrice;
    private List<BudgetLine> budgetLine;

    public BudgetWithBudgetLinesResponse() {
        budgetLine = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BudgetLine {
        private Integer treatmentId;
        private String treatmentName;
        private Integer quantity;
        private String teeth;
        private String discount;
        private String unitPrice;
        private String treatmentPrice;
        private String totalPrice;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status {
        private Integer id;
        private String description;
    }
}
