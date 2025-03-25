package com.romoreno.dentalium.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DicomImageMedicalStudy {
    private byte[] content;
    private String filename;
}
