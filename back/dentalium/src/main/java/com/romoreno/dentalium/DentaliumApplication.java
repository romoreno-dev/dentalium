package com.romoreno.dentalium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity")
public class DentaliumApplication {

    public static void main(String[] args) {
        SpringApplication.run(DentaliumApplication.class, args);
    }

}
