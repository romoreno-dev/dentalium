package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class ARestController {

    protected ResponseEntity<?> getResponseWithContentMediaType(MediaType mediaType, String filename, Object content) {
        final var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=%s", filename));

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(mediaType)
                .body(content);
    }

}
