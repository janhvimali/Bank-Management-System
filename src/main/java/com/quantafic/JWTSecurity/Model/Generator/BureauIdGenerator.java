package com.quantafic.JWTSecurity.Model.Generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class BureauIdGenerator implements IdentifierGenerator {
    private static String getRandomAlphaNumeric(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        String prefix = "Bureau";
        String provider = "CIBIL";
        String random = getRandomAlphaNumeric(6);
        return String.format("%s-%s-%s", prefix, provider, random);
    }
}
