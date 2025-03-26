package com.romoreno.dentalium.infraestructure.adapter.out.persistence.mapper;

import com.romoreno.dentalium.domain.model.Treatment;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.TreatmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = "spring")
public interface TreatmentEntityMapper {

    TreatmentEntityMapper INSTANCE = Mappers.getMapper(TreatmentEntityMapper.class);

    @Mapping(source = "active", target = "active", qualifiedByName = "booleanToShort")
    @Mapping(source = "unitPrice", target = "unitPrice", qualifiedByName = "bigDecimalToFloat")
    TreatmentEntity toEntity(Treatment treatment);

    @Mapping(source = "active", target = "active", qualifiedByName = "shortToBoolean")
    @Mapping(source = "unitPrice", target = "unitPrice", qualifiedByName = "floatToBigDecimal")
    Treatment toDomain(TreatmentEntity treatment);

    @Named("booleanToShort")
    default Short booleanToShort(Boolean value) {
        if (value == null) {
            return null;
        }
        return (short) (value ? 1 : 0);
    }

    @Named("shortToBoolean")
    default boolean shortToBoolean(Short value) {
        return value != null && value == 1;
    }

    @Named("bigDecimalToFloat")
    default Float bigDecimalToFloat(BigDecimal unitPrice) {
        if (unitPrice == null) {
            return null;
        }
        return unitPrice.setScale(2, RoundingMode.HALF_UP).floatValue();
    }

    @Named("floatToBigDecimal")
    default BigDecimal floatToBigDecimal(Float unitPrice) {
        if (unitPrice == null) {
            return null;
        }
        return new BigDecimal(unitPrice).setScale(2, RoundingMode.HALF_UP);
    }

}
