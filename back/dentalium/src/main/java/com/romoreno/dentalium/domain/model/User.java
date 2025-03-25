package com.romoreno.dentalium.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String email;
    private String phone;
    private String user;
    @JsonIgnore
    private String password;
    private String identification;
    private String address;
    private String postalCode;
    private String municipality;
    private String province;
    private String role;
    private boolean active;
    private UserReference userReference;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserReference {
        private String id;
        private String name;
    }
}
