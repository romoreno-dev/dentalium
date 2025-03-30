package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.treatment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentRestDTO {
    private String id;
    @NotNull(message = "Debe indicarse el nombre del tratamiento")
    @Size(max = 255, message = "El nombre del tratamiento no debe ser mayor de 255 caracteres")
    private String name;
    @NotNull(message = "Debe indicarse el precio unitario del tratamiento")
    private BigDecimal unitPrice;
    @NotNull(message = "Debe indicarse si el tratamiento está activo o no")
    private Boolean active;
}
