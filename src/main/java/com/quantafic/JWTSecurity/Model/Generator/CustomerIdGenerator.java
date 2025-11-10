package com.quantafic.JWTSecurity.Model.Generator;

// File: CustomerIdGenerator.java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class CustomerIdGenerator {

    public static String generate() {
        String prefix = "CUST";
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // yyyyMMdd
        String random = getRandomAlphaNumeric(6);
        return String.format("%s-%s-%s", prefix, date, random);
    }

    private static String getRandomAlphaNumeric(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}

