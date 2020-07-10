package com.github.landrev.rates;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashUtils {

    private static final String SHA_256_HASH = "SHA-256";

    private HashUtils() {}

    public static String generateSha256Hash(String input) {
        MessageDigest messageDigest;

        try {
            messageDigest = MessageDigest.getInstance(SHA_256_HASH);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] hash = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
        return mapToHexString(hash);
    }

    public static boolean checkSha256Hash(String input, String hash) {
        String calculatedHash = generateSha256Hash(input);
        return calculatedHash.equals(hash);
    }

    private static String mapToHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
}
