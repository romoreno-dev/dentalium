package com.romoreno.dentalium.domain.port.in;

import com.romoreno.dentalium.domain.model.DicomImageMedicalStudy;
import com.romoreno.dentalium.domain.model.MedicalStudy;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.medical_study.dto.DicomResponse;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.medical_study.dto.ListMedicalStudiesResponse;

public interface MedicalStudyPort {

    ListMedicalStudiesResponse.MedicalStudy uploadMedicalStudy(byte[] fileByte, String filename, String patientId,
                               String doctorId, Integer contentType) throws Exception;

    MedicalStudy downloadMedicalStudy(String id);

    DicomImageMedicalStudy downloadDICOMContent(String id);

    DicomResponse downloadDICOMData(String id);

    ListMedicalStudiesResponse listMedicalStudies(String patientId);

    boolean deleteMedicalStudy(Integer id);
}
