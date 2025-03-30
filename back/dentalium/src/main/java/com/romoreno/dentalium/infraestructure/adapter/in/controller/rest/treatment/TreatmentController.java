package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.treatment;

import com.romoreno.dentalium.application.services.BudgetService;
import com.romoreno.dentalium.domain.model.Treatment;
import com.romoreno.dentalium.domain.port.in.TreatmentPort;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.Constants;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.treatment.dto.TreatmentResponse;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.treatment.dto.TreatmentRestDTO;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.treatment.mapper.TreatmentMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.Controller.TREATMENT)
@Tag(name = "Treatment", description = "Operaciones relacionadas con la gestión de tratamientos")
public class TreatmentController {

    private final TreatmentMapper treatmentMapper;
    private final TreatmentPort treatmentPort;
    private final BudgetService budgetService;

    public TreatmentController(TreatmentMapper treatmentMapper, TreatmentPort treatmentPort, BudgetService budgetService) {
        this.treatmentMapper = treatmentMapper;
        this.treatmentPort = treatmentPort;
        this.budgetService = budgetService;
    }

    @GetMapping(Constants.PathVariable.ID)
    @Operation(summary = "Obtener tratamiento con el id indicado")
    public ResponseEntity<TreatmentResponse> getTreatment(@PathVariable final String id) {
        final var treatment = new Treatment(id);

        final var treatmentList = treatmentPort.getTreatments(treatment);
        final var treatmentRest = treatmentList.stream()
                .map(treatmentMapper::toApi).toList();

        return ResponseEntity.ok(new TreatmentResponse(treatmentRest));
    }

    @PostMapping(Constants.Path.GET)
    @Operation(summary = "Obtener tratamientos segun el filtro seleccionado")
    public ResponseEntity<TreatmentResponse> getTreatments(@RequestBody TreatmentRestDTO treatmentRestDTO) {
        final var treatment = treatmentMapper.toDomain(treatmentRestDTO);

        final var treatmentList = treatmentPort.getTreatments(treatment);
        final var treatmentRest = treatmentList.stream()
                .map(treatmentMapper::toApi).toList();

        return ResponseEntity.ok(new TreatmentResponse(treatmentRest));
    }

    @PostMapping(Constants.Path.SAVE)
    @Operation(summary = "Guardar o actualizar tratamiento")
    public ResponseEntity<Boolean> saveTreatment(@Valid @RequestBody TreatmentRestDTO treatmentRestDTO) {
        final var treatment = treatmentMapper.toDomain(treatmentRestDTO);

        final var success = treatmentPort.saveTreatment(treatment);

        return ResponseEntity.ok(success);
    }

    @DeleteMapping(Constants.PathVariable.ID)
    @Operation(summary = "Eliminar tratamiento con el id indicado")
    public ResponseEntity<Boolean> deleteTreatment(@PathVariable final int id) {
        final var success = treatmentPort.deleteTreatment(id);

        return ResponseEntity.ok(success);
    }

    //todo Quizas en el futuro un endpoint para habilitar y deshabilitar tratamientos

}
