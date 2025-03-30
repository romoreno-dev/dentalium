package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.login;

import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.Constants;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.Controller.LOGIN)
public class LoginController {

    @GetMapping
    @Operation(summary = "Servicio para hacer login en la API")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok().body("{\"result\":\"OK\"}");
    }


}
