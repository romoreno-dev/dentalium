package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestRestDTO {

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
    private String role;

}
