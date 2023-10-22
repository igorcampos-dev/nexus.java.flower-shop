package com.flowershop.back.services.impl;

import com.flowershop.back.services.ReadersService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Log4j2
public class ReadersImpl implements ReadersService {

    @Override
    @SneakyThrows
    public String fileHtmlConfirmacao(String html) {
        String filePath = "src/main/resources/messages/confirmacao/" + html;
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    @Override
    @SneakyThrows
    public String fileSendEmail(String html) {
        String filePath = "src/main/resources/messages/sendEmail/" + html;
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
