package com.flowershop.back.services.impl.email;

import com.flowershop.back.domain.ReturnResponseBody;
import com.flowershop.back.domain.flower.MessageDTO;
import com.flowershop.back.domain.flower.ResponseFlowerGet;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.services.EmailService;
import com.flowershop.back.services.ReadersService;
import com.flowershop.back.services.repo.FlowerMethodsDbs;
import com.flowershop.back.services.repo.UserMethodsDbs;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import static com.flowershop.back.configuration.UtilsProject.randomHash;
import static com.flowershop.back.configuration.UtilsProject.replaceFilename;


@Service
public class EmailServiceImpl implements EmailService {
    private final ReadersService readersService;
    private final FlowerMethodsDbs flowerMethodsDbs;
    private final UserMethodsDbs userMethodsDbs;

    public EmailServiceImpl(ReadersService readersService, FlowerMethodsDbs flowerMethodsDbs, UserMethodsDbs userMethodsDbs) {
        this.readersService = readersService;
        this.flowerMethodsDbs = flowerMethodsDbs;
        this.userMethodsDbs = userMethodsDbs;
    }

    @Value("${api.java.mail.email}")
    private String emailAdmin;
    @Value("${api.java.mail.password}")
    private String passwordAdmin;
    @Value("${api.link.address}")
    private String link;

    @Override
    public void sendEmailVerification(String email, String hash) {
        String url = link + "/confirme-email?hash=" + hash;
        String mensagem = String.format(readersService.fileHtml("Confirmacao"), url);
        send(email, "Confirmação de cadastro", mensagem, null, null);
    }

    @SneakyThrows
    @Override
    public void sendEmailUser(MessageDTO message) {
        userMethodsDbs.userExistsByHash(message.hash());
        ResponseFlowerGet flower = flowerMethodsDbs.findByFilename(replaceFilename(message.flower()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(flower.file()))), "jpg", baos);
        Resource imageResource = new ByteArrayResource(baos.toByteArray());
        String mensagemFinal = String.format(readersService.fileHtml("EnvioFlor"), flower.fileName(), message.mensagem());

        send(message.email(), "Uma flor linda para uma pessoa linda \uD83D\uDE0A", mensagemFinal, flower.fileName().concat(".jpg"), imageResource);
    }

    @Override
    public JavaMailSender MailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(emailAdmin);
        mailSender.setPassword(passwordAdmin);
        mailSender.setDefaultEncoding("UTF-8");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");
        return mailSender;
    }

    @Override
    public void sendEmailResetPass(String email, String hash) {
        String newPass = randomHash(12);

        User user = userMethodsDbs.findByLoginAndHash(email, hash);
        user.setPassword(new BCryptPasswordEncoder().encode(newPass));
        userMethodsDbs.save(user);

        String mensagemFinal = String.format(readersService.fileHtml("RecuperarSenha"), newPass);
        send(email,"Recuperação de senha", mensagemFinal, null, null);
    }

    @Override
    @SneakyThrows
    public void send(String email, String assunto, String mensagem, String filename, Resource image)  {
            try {
                JavaMailSender emailSender = MailSender();
                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setFrom(emailAdmin);
                helper.setTo(email.toLowerCase());
                helper.setSubject(assunto);
                helper.setSentDate(new Date());
                helper.setText(mensagem, true);
                if (filename != null && image != null){ helper.addAttachment(Objects.requireNonNull(filename), image);}
                emailSender.send(message);

                new ReturnResponseBody("Email enviado com sucesso");
            } catch (MessagingException e) {
                throw new MessagingException("O servidor encontrou uma falha ao tentar enviar o email. Por favor, tente novamente mais tarde.");
            }
    }
}
