package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.assignment;

import com.romoreno.dentalium.domain.port.in.AssignmentPort;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.Constants;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.assignment.dto.AssignDoctorRequest;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.assignment.dto.ListAssigmentResponse;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.assignment.mapper.AssigmentMapper;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.Controller.ASSIGNMENT)
@Tag(name = "Assigment", description = "Operaciones relacionadas con la asignación de pacientes y doctores")
public class AssigmentController {

    private final AssigmentMapper assigmentMapper;
    private final AssignmentPort assignmentPort;

    public AssigmentController(AssigmentMapper assigmentMapper, AssignmentPort assignmentPort) {
        this.assigmentMapper = assigmentMapper;
        this.assignmentPort = assignmentPort;
    }

    @GetMapping
    @Operation(summary = "Listar asignaciones")
    public ResponseEntity<ListAssigmentResponse> listAssigment(@RequestParam(value = "idPatient", required = false) String idPatient,
                                                               @RequestParam(value = "idDoctor", required = false) String idDoctor) throws Exception {
        if (StringUtils.isBlank(idPatient) && StringUtils.isBlank(idDoctor)) {
            throw new BadRequestException();
        }
        return ResponseEntity.ok().body(assignmentPort.listAssigment(idPatient, idDoctor));
    }

    @PostMapping(Constants.Path.MODIFY)
    @Operation(summary = "Asignar un dentista a un paciente")
    public ResponseEntity<Boolean> assignDoctor(@RequestBody AssignDoctorRequest request) {
        return ResponseEntity.ok().body(assignmentPort.assignDoctor(request.getPatientId(), request.getDoctorId()));
    }


}
