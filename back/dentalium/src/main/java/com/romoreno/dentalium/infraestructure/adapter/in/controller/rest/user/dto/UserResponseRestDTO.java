package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class UserResponseRestDTO {

    private List<User> user;

    public UserResponseRestDTO() {
        user = new ArrayList<>();
    }

    public static class User {
        private String id;
        private String name;
        private String firstSurname;
        private String secondSurname;
        private String email;
        private String phone;
        private String user;
        private String password;
        private String identification;
        private String address;
        private String postalCode;
        private String municipality;
        private String province;

    }

}
