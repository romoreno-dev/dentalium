package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.medical_study;

import com.romoreno.dentalium.domain.port.in.MedicalStudyPort;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.ARestController;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.Constants;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.medical_study.dto.DicomResponse;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.medical_study.dto.ListMedicalStudiesResponse;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.medical_study.mapper.MedicalStudyMapper;
import com.romoreno.dentalium.infraestructure.config.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;

@RestController
@RequestMapping(Constants.Controller.MEDICAL_STUDY)
@Tag(name = "Study", description = "Operaciones relacionadas con la gestión de los estudios médicos del paciente")
public class MedicalStudyController extends ARestController {

    private final MedicalStudyMapper medicalStudyMapper;
    private final MedicalStudyPort medicalStudyPort;

    public MedicalStudyController(MedicalStudyMapper medicalStudyMapper, MedicalStudyPort medicalStudyPort) {
        this.medicalStudyMapper = medicalStudyMapper;
        this.medicalStudyPort = medicalStudyPort;
    }

    @GetMapping
    @Operation(summary = "Listar los estudios medicos de un paciente")
//    @PreAuthorize("hasRole('DENTIST') or #patientId == authentication.principal.id")
    public ResponseEntity<ListMedicalStudiesResponse> listMedicalStudies(@RequestParam String patientId) {
        return ResponseEntity.ok().body(medicalStudyPort.listMedicalStudies(patientId));
    }

    @GetMapping(Constants.PathVariable.ID + Constants.Path.DOWNLOAD)
    @Operation(summary = "Descargar estudio médico")
    public ResponseEntity<?> downloadMedicalStudies(@PathVariable String id) throws Exception {
        try {
            final var medicalStudy = medicalStudyPort.downloadMedicalStudy(id);
            final var path = medicalStudy.getPath();
            final var resource = new FileSystemResource(path);
            final var contentType = MediaType.parseMediaType(StringUtils.defaultIfBlank(Files.probeContentType(path), "application/octet-stream"));

            return getResponseWithContentMediaType(contentType, medicalStudy.getFileName(), resource);
        } catch (Exception e) {
            System.out.println(e.getCause());
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(Constants.PathVariable.ID + Constants.Path.DICOM)
    @Operation(summary = "Descargar imagen contenida en fichero DICOM")
    public ResponseEntity<?> downloadDICOMImage(@PathVariable String id) throws Exception {
        final var medicalStudy = medicalStudyPort.downloadDICOMContent(id);
        return getResponseWithContentMediaType(MediaType.IMAGE_JPEG, medicalStudy.getFilename(), medicalStudy.getContent());
    }

    @GetMapping(Constants.PathVariable.ID + Constants.Path.DICOM + Constants.Path.DATA)
    @Operation(summary = "Extraer datos contenidos en fichero DICOM")
    public ResponseEntity<DicomResponse> downloadDICOMData(@PathVariable String id) throws Exception {
        final var medicalStudy = medicalStudyPort.downloadDICOMData(id);
        return ResponseEntity.ok().body(medicalStudy);
    }

    @PostMapping(Constants.Path.UPLOAD)
    @Operation(summary = "Subir estudio médico de un paciente")
    public ResponseEntity<ListMedicalStudiesResponse.MedicalStudy> uploadMedicalStudy(@RequestParam("userId") String userId,
                                                      @RequestParam("type") Integer type,
                                                      @RequestParam("file") MultipartFile file, Authentication authentication) {
        if (file == null) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            final var userDetails = (CustomUserDetails) authentication.getPrincipal();
            final var result = medicalStudyPort.uploadMedicalStudy(file.getBytes(), file.getOriginalFilename(), userId, userDetails.getId(),
                    type);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping(Constants.PathVariable.ID)
    @Operation(summary = "Eliminar estudio médico según el ID indicado")
    public ResponseEntity<Boolean> deleteMedicalStudy(@PathVariable Integer id) {
        return ResponseEntity.ok().body(medicalStudyPort.deleteMedicalStudy(id));
    }

}
