package com.flowershop.back.configuration;

import java.security.SecureRandom;

public class UtilsProject {

    private static String AB = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomHash() {
        StringBuilder sb = new StringBuilder(48);
        for (int i = 0; i < 48; i++) sb.append(AB.charAt(new SecureRandom().nextInt(AB.length())));
        return sb.toString();
    }


}
