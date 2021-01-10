package ru.klonwar.checkers.models.database;

import java.math.BigInteger;
import java.security.MessageDigest;

public final class MD5 {
    public static String getMD5(String from) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(from.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(no.toString(16));
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }
            return hashtext.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
