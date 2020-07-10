package com.github.landrev.rates;

import java.security.SecureRandom;

public final class RandomUtils {

    private static final SecureRandom rand;
    private static final String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private RandomUtils() {}

    static {
        rand = new SecureRandom();
    }

    public static String getRandomString(int length) {
        return rand.ints(length, 0, alphabet.length())
                .mapToObj(alphabet::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
