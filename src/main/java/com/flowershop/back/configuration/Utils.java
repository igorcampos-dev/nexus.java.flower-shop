package com.flowershop.back.configuration;

import jakarta.annotation.Nullable;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

public class Utils {

    static final String AB = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    static SecureRandom rnd = new SecureRandom();


    public static String randomHash() {
        StringBuilder sb = new StringBuilder(48);
        for (int i = 0; i < 48; i++) sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
    public static String fileHtml(String nameFile) throws IOException {
        String filePath = "src/main/resources/templates/" + nameFile + ".html";
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }


    public static boolean isEmailValid(String email) {
        try {
            new InternetAddress(email).validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }

}
