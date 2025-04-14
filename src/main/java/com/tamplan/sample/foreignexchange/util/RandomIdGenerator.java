package com.tamplan.sample.foreignexchange.util;

public class RandomIdGenerator {

    public static String generateRandomId() {
        return java.util.UUID.randomUUID().toString();
    }
}
