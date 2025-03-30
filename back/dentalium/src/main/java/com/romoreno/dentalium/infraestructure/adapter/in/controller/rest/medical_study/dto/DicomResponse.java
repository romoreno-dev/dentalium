package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.medical_study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class DicomResponse {

    private ListMedicalStudiesResponse.MedicalStudy medicalStudy;
    private List<DicomAttributes> dicomAttributes;
    private String base64Content;

    public DicomResponse() {
        dicomAttributes = new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DicomAttributes {
        private String value;
        private String description;
    }


}
