package com.romoreno.dentalium.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Treatment {
    private String id;
    private String name;
    private BigDecimal unitPrice;
    private Boolean active;

    public Treatment(String id) {
        this.id = id;
    }
}
