package com.romoreno.dentalium.infraestructure.config.security;

import io.micrometer.common.util.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        final var origins = new ArrayList<String>();
        final var frontendUrl1 = System.getenv("FRONTEND_URL");
        final var frontendUrl2 = System.getenv("FRONTEND_URL_2");
        System.out.println("FrontendUrl1: " + frontendUrl1);
        System.out.println("FrontendUrl2: " + frontendUrl2);
        if (StringUtils.isNotBlank(frontendUrl1)) {
            origins.add(frontendUrl1);
        }
        if (StringUtils.isNotBlank(frontendUrl2)) {
            origins.add(frontendUrl2);
        }
        System.out.println(frontendUrl1);

        registry.addMapping("/**")
                .allowedOrigins(origins.toArray(new String[0]))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")  // Permite todos los encabezados
                .exposedHeaders("Authorization", "X-User-Role", "X-User-Id", "Content-Disposition", "Content-Type", "Content-Length")
                .allowCredentials(true);
    }
}