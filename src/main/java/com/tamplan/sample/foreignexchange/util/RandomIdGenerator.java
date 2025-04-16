package com.tamplan.sample.foreignexchange.util;

public class RandomIdGenerator {

    private RandomIdGenerator() {
    }

    public static String generateRandomId() {
        return java.util.UUID.randomUUID().toString();
    }
}
