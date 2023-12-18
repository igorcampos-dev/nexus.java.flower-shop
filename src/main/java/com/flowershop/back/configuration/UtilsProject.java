package com.flowershop.back.configuration;

import java.security.SecureRandom;

public class UtilsProject {

    private static String AB = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomHash(int capacity) {
        StringBuilder sb = new StringBuilder(capacity);
        for (int i = 0; i < capacity; i++) sb.append(AB.charAt(new SecureRandom().nextInt(AB.length())));
        return sb.toString();
    }

    public static String replaceFilename(String filename) {
        return  filename.replace("%20", " ");
    }


}
