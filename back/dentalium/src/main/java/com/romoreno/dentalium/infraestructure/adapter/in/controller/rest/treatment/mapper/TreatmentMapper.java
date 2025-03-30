package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.treatment.mapper;

import com.romoreno.dentalium.domain.model.Treatment;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.treatment.dto.TreatmentRestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TreatmentMapper {

    TreatmentMapper INSTANCE = Mappers.getMapper(TreatmentMapper.class);

    Treatment toDomain(TreatmentRestDTO treatmentRestDTO);

    TreatmentRestDTO toApi(Treatment treatment);


}
