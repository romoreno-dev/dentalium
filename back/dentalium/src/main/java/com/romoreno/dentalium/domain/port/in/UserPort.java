package com.romoreno.dentalium.domain.port.in;

import com.romoreno.dentalium.domain.model.User;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.user.dto.UserSearchResponse;

import java.util.List;

public interface UserPort {

    User getUser(String name);

    List<User> getUsers(User user);

    UserSearchResponse getPatients(String identification);

    boolean createUser(User user, String requestAuthor);

    UserSearchResponse getAllPatients();

    UserSearchResponse getAllDoctors();

    boolean deleteUser(Long id, String requestAuthor);
    boolean changePassword(String email);
    boolean activateUser(Long id, boolean active);

    }
