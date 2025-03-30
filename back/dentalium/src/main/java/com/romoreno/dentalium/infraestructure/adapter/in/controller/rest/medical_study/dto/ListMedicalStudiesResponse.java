package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.medical_study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class ListMedicalStudiesResponse {

    private String patientId;
    private List<MedicalStudy> medicalStudies;

    public ListMedicalStudiesResponse() {
        medicalStudies = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MedicalStudy {
        private Integer id;
        private Integer doctorId;
        private StudyType studyType;
        private Date date;
        private String filename;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StudyType {
        private Integer id;
        private String description;
    }
}
