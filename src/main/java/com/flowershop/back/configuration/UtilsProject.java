package com.flowershop.back.configuration;

import lombok.SneakyThrows;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

public class UtilsProject {

    private static String AB = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomHash() {
        StringBuilder sb = new StringBuilder(48);
        for (int i = 0; i < 48; i++) sb.append(AB.charAt(new SecureRandom().nextInt(AB.length())));
        return sb.toString();
    }

    @SneakyThrows
    public static String fileHtml(String nameFile){
        String filePath = Files.exists(Paths.get("src/main/resources/templates/" + nameFile + ".html"))
                ? "src/main/resources/templates/" + nameFile + ".html"
                : "app/templates/" + nameFile + ".html";
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }


}
