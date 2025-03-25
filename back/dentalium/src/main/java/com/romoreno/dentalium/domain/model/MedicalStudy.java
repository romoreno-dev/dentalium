package com.romoreno.dentalium.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.Path;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalStudy {
    private Path path;
    private String fileName;
}
