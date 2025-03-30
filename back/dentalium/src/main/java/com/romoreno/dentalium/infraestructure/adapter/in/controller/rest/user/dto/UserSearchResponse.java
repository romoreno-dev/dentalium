package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.user.dto;

import com.romoreno.dentalium.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchResponse {

    private List<User> users;

}
