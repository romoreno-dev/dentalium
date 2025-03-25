package com.romoreno.dentalium.application.utils;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.UserEntity;
import org.apache.commons.lang3.StringUtils;

public class Utils {

    public static String extractPersonalData(UserEntity user) {
        return String.format("%s %s, %s",
                StringUtils.defaultIfBlank(user.getSurname1(), "").toUpperCase(),
                StringUtils.defaultIfBlank(user.getSurname2(), "").toUpperCase(),
                StringUtils.defaultIfBlank(user.getName(), "").toUpperCase());
    }
}
