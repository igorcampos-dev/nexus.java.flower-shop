package com.flowershop.back.services.impl;

import com.flowershop.back.services.ReadersService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReadersImpl implements ReadersService {

    @Override
    @SneakyThrows
    public String fileHtmlConfirmacao(String html) {
        String filePath = "src/main/resources/messages/actions/" + html;
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    @Override
    @SneakyThrows
    public String usePhrases(String tag) {
        Path filePath = Paths.get("src/main/resources/messages/messages.html");
        String conteudo = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
        Matcher matcher = Pattern.compile(String.format("<%s.*?>(.*?)</%s>", tag, tag), Pattern.DOTALL).matcher(conteudo);
        return matcher.find() ? matcher.group(1).trim() : "";
    }

    @Override
    @SneakyThrows
    public String fileHtml(String filename) {
        Path filePath = Paths.get("src/main/resources/messages/actions/" + filename + ".html");
        byte[] fileBytes = Files.readAllBytes(filePath);
        String conteudo = new String(fileBytes, StandardCharsets.UTF_8);

        Pattern pattern = Pattern.compile("<html.*?>(.*?)</html>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(conteudo);

        return matcher.find() ? matcher.group(1).trim() : "";
    }
}
