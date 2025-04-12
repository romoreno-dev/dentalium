package com.romoreno.dentalium.application.utils;

import java.security.SecureRandom;

public class SecureUtils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
    private static final SecureRandom random = new SecureRandom();

    public static String generatePassword(int length) {
        final var password = new StringBuilder(length);

        for (var i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }
}