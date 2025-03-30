package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.user;

import com.romoreno.dentalium.domain.model.User;
import com.romoreno.dentalium.domain.port.in.UserPort;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.Constants;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.user.dto.UserSearchResponse;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.user.dto.UserRequestRestDTO;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.user.dto.UserResponseRestDTO;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.user.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.Controller.USER)
public class UserController {

    private final UserMapper userMapper;
    private final UserPort userPort;

    public UserController(UserMapper userMapper, UserPort userPort) {
        this.userMapper = userMapper;
        this.userPort = userPort;
    }

    @GetMapping(Constants.PathVariable.ID)
    @Operation(summary = "Obtener usuario con el id indicado")
    public ResponseEntity<UserResponseRestDTO> getUser(@PathVariable final Integer id) {
        final var user = new User();
        user.setId(id);

        final var userList = userPort.getUsers(user);
        final var userRest = userList.stream()
                .map(userMapper::toApi).toList();

        return ResponseEntity.ok(new UserResponseRestDTO(userRest));
    }

    @GetMapping(Constants.Path.GET + Constants.PathVariable.ID)
    @Operation(summary = "Obtener usuarios segun el filtro seleccionado")
    public ResponseEntity<UserSearchResponse> getPatients(@PathVariable String id) {
        return ResponseEntity.ok(userPort.getPatients(id));
    }

    @GetMapping(Constants.Path.GET + Constants.Path.PATIENT)
    @Operation(summary = "Obtener todos los usuarios")
    public ResponseEntity<UserSearchResponse> getAllPatients() {
        return ResponseEntity.ok(userPort.getAllPatients());
    }

    @GetMapping(Constants.Path.GET + Constants.Path.DOCTOR)
    @Operation(summary = "Obtener todos los dentistas")
    public ResponseEntity<UserSearchResponse> getAllDoctors() {
        return ResponseEntity.ok(userPort.getAllDoctors());
    }

    @PostMapping
    @Operation(summary = "Guardar o actualizar usuario")
    public ResponseEntity<Boolean> saveUser(@RequestBody UserRequestRestDTO request) {
        userPort.createUser(userMapper.toDomain(request), "");
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    @DeleteMapping(Constants.PathVariable.ID)
    @Operation(summary = "Eliminar usuario con el id indicado")
    public ResponseEntity<Boolean> deleteUser(@PathVariable final long id) {
        final var success = userPort.deleteUser(id, "");

        return ResponseEntity.ok(success);
    }

    @GetMapping( Constants.Path.CHANGE)
    @Operation(summary = "Cambiar y enviar correo de recuperación de contraseña")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Boolean> changePassword(@RequestParam("email") String email) {
        final var success = userPort.changePassword(email);
        return ResponseEntity.ok(success);
    }

    @GetMapping( Constants.PathVariable.ID+Constants.Path.CHANGE+Constants.Path.ACTIVATE)
    @Operation(summary = "Cambiar y enviar correo de recuperación de contraseña")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Boolean> activateUser(@PathVariable final long id, @RequestParam("active") boolean active) {
        final var success = userPort.activateUser(id, active);
        return ResponseEntity.ok(success);
    }




}
