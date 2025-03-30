package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.budget;

import com.romoreno.dentalium.domain.port.in.BudgetPort;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.ARestController;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.Constants;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.budget.dto.*;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.budget.mapper.BudgetMapper;
import com.romoreno.dentalium.infraestructure.config.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.Controller.BUDGET)
@Tag(name = "Budget", description = "Operaciones relacionadas con la gestión de presupuestos")
public class BudgetController extends ARestController {

    private final BudgetMapper budgetMapper;
    private final BudgetPort budgetPort;

    public BudgetController(BudgetMapper budgetMapper, BudgetPort budgetPort) {
        this.budgetMapper = budgetMapper;
        this.budgetPort = budgetPort;
    }

    @GetMapping
    @Operation(summary = "Listar los presupuestos de un paciente")
    @PreAuthorize("hasRole('DENTIST') or #patientId == authentication.principal.id")
    public ResponseEntity<BudgetResponse> listBudgets(@RequestParam String patientId) {
        return ResponseEntity.ok().body(budgetPort.listBudgets(patientId));
    }

    @GetMapping(Constants.PathVariable.ID)
    @Operation(summary = "Detallar el presupuesto cuyo ID se indica")
    public ResponseEntity<BudgetWithBudgetLinesResponse> detailBudget(@PathVariable String id, Authentication authentication) {
        final var response = budgetPort.detailBudget(id);

        final var userDetails = (CustomUserDetails) authentication.getPrincipal();
        if (userDetails.getId().equals(response.getPatientId()) ||
                authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_DENTIST"))) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @GetMapping(Constants.PathVariable.ID + Constants.Path.DOWNLOAD)
    @Operation(summary = "Descargar en PDF el presupuesto cuyo ID se indica")
   // @PreAuthorize("permitAll()") // no autenticado
    //@PreAuthorize("hasRole('DENTIST')") //autenticado y autorizado
    // Si no pongo nada, entra cualquier autenticado pero no tiene porque estar autorizado
    // SecurityContextHolder.getContext().getAuthentication()
    public ResponseEntity<?> downloadBudget(@PathVariable int id, @RequestParam(required = false) boolean signed) throws Exception {
        //fixme El usuario no debe poder descargar cualquier presupuesto sino solo uno que sea suyo (Identificar que lo sea)
        final var bytePdfContent = budgetPort.generatePdfBudget(id, signed);
        return getResponseWithContentMediaType(MediaType.APPLICATION_PDF,
                String.format("budget_%s.pdf", id),
                bytePdfContent);
    }

    @PostMapping(Constants.Path.SAVE)
    @Operation(summary = "Creación o actualización de un presupuesto")
    public ResponseEntity<Boolean> saveBudget(@RequestBody BudgetRequestRestDTO request) throws Exception {
        return ResponseEntity.ok().body(budgetPort.saveBudget(request));
    }

    @PostMapping(Constants.Path.STATUS)
    @Operation(summary = "Modificar el estado de un presupuesto")
    public ResponseEntity<Boolean> modifyStatus(@RequestBody ModifyStatusBudgetRequest statusBudget) throws Exception {
        return ResponseEntity.ok().body(budgetPort.modifyStatus(statusBudget));
    }

    @PostMapping(Constants.PathVariable.ID + Constants.Path.CONTENT)
    @Operation(summary = "Añadir tratamiento al presupuesto")
    public ResponseEntity<Boolean> includeTreatment(@PathVariable String id, @RequestBody BudgetLineRestDTO budgetLine) throws Exception {
        return ResponseEntity.ok().body(budgetPort.includeTreatment(id, budgetLine));
    }

    @DeleteMapping(Constants.PathVariable.ID + Constants.Path.CONTENT + Constants.PathVariable.TREATMENT_ID)
    @Operation(summary = "Eliminar un tratamiento del presupuesto")
    public ResponseEntity<Boolean> deleteTreatment(@PathVariable String id, @PathVariable String treatmentId) throws Exception {
        return ResponseEntity.ok().body(budgetPort.deleteTreatment(id, treatmentId));
    }

    @DeleteMapping(Constants.PathVariable.ID)
    @Operation(summary = "Eliminar un presupuesto")
    public ResponseEntity<Boolean> deleteBudget(@PathVariable String id) {
        return ResponseEntity.ok().body(budgetPort.deleteBudget(id));
    }

}
