package com.nexus.back.services.impl.email;

import com.nexus.back.domain.dto.flower.MessageDTO;
import com.nexus.back.domain.dto.flower.ResponseFlowerGet;
import com.nexus.back.properties.AdressProperties;
import com.nexus.mail.models.EmailProperties;
import com.nexus.utils.Readers;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Date;

@Service
@AllArgsConstructor
public class EmailMethodsSupport {

    private final Readers readersService;
    private final AdressProperties properties;

    @SneakyThrows
    public EmailProperties getMessageConfirmation(String hash,String email){
        String url = properties.getAdress().concat("/confirme-email?hash=").concat(hash);
        String message = String.format(readersService.fileHtml("Confirmacao"), url);

        return EmailProperties.builder()
                .email(email)
                .subject("Confirmação de cadastro")
                .messages(message)
                .filename(null)
                .image(null)
                .date(new Date())
                .multiPart(false)
                .build();
    }

    @SneakyThrows
    public Resource createImage(ResponseFlowerGet flower){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(flower.file()))), "jpg", baos);
        return new ByteArrayResource(baos.toByteArray());
    }

    @SneakyThrows
    public EmailProperties getMessageFlower(ResponseFlowerGet flower, MessageDTO message, Resource image){
        String messages = String.format(readersService.fileHtml("EnvioFlor"), flower.fileName(), message.mensagem());

        return EmailProperties.builder()
                .email(message.email())
                .subject("Uma flor linda para uma pessoa linda \uD83D\uDE0A")
                .messages(messages)
                .filename(flower.fileName().concat(".jpg"))
                .image(image)
                .date(new Date())
                .multiPart(true)
                .build();
    }

    @SneakyThrows
    public EmailProperties getMessageRecoveryPass(String newPass, String email){
        String message = String.format(readersService.fileHtml("RecuperarSenha"), newPass);

        return EmailProperties.builder()
                .email(email)
                .subject("Recuperação de senha")
                .messages(message)
                .filename(null)
                .image(null)
                .date(new Date())
                .multiPart(false)
                .build();
    }

}
