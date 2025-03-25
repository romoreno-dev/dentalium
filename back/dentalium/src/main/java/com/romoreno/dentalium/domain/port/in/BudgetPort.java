package com.romoreno.dentalium.domain.port.in;

import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.budget.dto.*;

public interface BudgetPort {

    byte[] generatePdfBudget(int id, boolean signed) throws Exception;

    boolean saveBudget(BudgetRequestRestDTO budget);

    boolean includeTreatment(String id, BudgetLineRestDTO budgetLine);

    boolean deleteTreatment(String id, String treatmentId);

    boolean deleteBudget(String id);

    boolean modifyStatus(ModifyStatusBudgetRequest request);

    BudgetResponse listBudgets(String patientId);

    BudgetWithBudgetLinesResponse detailBudget(String id);
}
