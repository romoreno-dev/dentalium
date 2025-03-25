package com.romoreno.dentalium.infraestructure.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Dentalium API", description = "Especificaciones de uso de Dentalium API",
        contact = @Contact(name = "romorenodev", url = "http://github.com/romoreno-dev", email = "romorenodev@gmail.com"),
        version = "1.0.0"))
public class SwaggerConfig {
}
