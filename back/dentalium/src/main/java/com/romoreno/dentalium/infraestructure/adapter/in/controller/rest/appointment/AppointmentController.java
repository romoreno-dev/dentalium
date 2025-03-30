package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment;

import com.romoreno.dentalium.domain.port.in.AppointmentPort;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.ARestController;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.Constants;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto.ModifyStatusAppointmentRequest;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto.SaveAppointmentRequest;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto.SearchAppointmentRequest;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto.SearchAppointmentResponse;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.mapper.AppointmentMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.Controller.APPOINTMENT)
@Tag(name = "Appointment", description = "Operaciones relacionadas con la gestión de citas")
public class AppointmentController extends ARestController {

    private final AppointmentMapper appointmentMapper;
    private final AppointmentPort appointmentPort;

    public AppointmentController(AppointmentMapper appointmentMapper, AppointmentPort appointmentPort) {
        this.appointmentMapper = appointmentMapper;
        this.appointmentPort = appointmentPort;
    }

    @GetMapping(Constants.Controller.CERTIFICATE + Constants.PathVariable.ID)
    @Operation(summary = "Emitir certificado del estado de una cita")
    public ResponseEntity<?> generateAppointmentCertificate(@PathVariable int id, @RequestParam(required = false) boolean signed) throws Exception {
        //fixme El usuario no debe poder descargar cualquier certificado sino solo uno que sea suyo (Identificar que lo sea)
        final var bytePdfContent = appointmentPort.generatePdfCertificate(id, signed);
        if (bytePdfContent == null) {
            return ResponseEntity.badRequest().build();
        }
        return getResponseWithContentMediaType(MediaType.APPLICATION_PDF,
                String.format("certificate_%s.pdf", id),
                bytePdfContent);
    }

    @PostMapping(Constants.Path.GET)
    @Operation(summary = "Obtener citas con los filtros indicados")
    public ResponseEntity<SearchAppointmentResponse> searchAppointments(@RequestBody SearchAppointmentRequest request) {
        final var appointment = appointmentPort.getAppointments(request);
        return ResponseEntity.ok(appointment);
    }

    @PostMapping(Constants.Path.SAVE)
    @Operation(summary = "Guardar o actualizar una cita")
    public ResponseEntity<Boolean> saveAppointment(@RequestBody SaveAppointmentRequest request) throws Exception {
        appointmentPort.saveAppointment(request);
        return ResponseEntity.ok(true);
    }

    @PostMapping(Constants.Path.STATUS)
    @Operation(summary = "Modifica el estado de un cita")
    public ResponseEntity<Boolean> modifyStatus(@RequestBody ModifyStatusAppointmentRequest request) throws Exception {
        appointmentPort.modifyStatus(request);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping(Constants.PathVariable.ID)
    @Operation(summary = "Eliminar una cita con el id indicado")
    public ResponseEntity<Boolean> deleteAppointment(@PathVariable Integer id) {
        final var success = appointmentPort.deleteAppointment(id);
        return ResponseEntity.ok(success);
    }

}
