package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.user.mapper;

import com.romoreno.dentalium.domain.model.User;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.user.dto.UserRequestRestDTO;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.user.dto.UserResponseRestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toDomain(UserRequestRestDTO userRequestRestDTO);

    UserResponseRestDTO.User toApi(User user);

}
