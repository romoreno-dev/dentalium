package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.assignment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AssigmentMapper {

    AssigmentMapper INSTANCE = Mappers.getMapper(AssigmentMapper.class);

}
