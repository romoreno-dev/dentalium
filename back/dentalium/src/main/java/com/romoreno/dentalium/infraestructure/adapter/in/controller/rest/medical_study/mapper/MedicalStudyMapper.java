package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.medical_study.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MedicalStudyMapper {

    MedicalStudyMapper INSTANCE = Mappers.getMapper(MedicalStudyMapper.class);

}
