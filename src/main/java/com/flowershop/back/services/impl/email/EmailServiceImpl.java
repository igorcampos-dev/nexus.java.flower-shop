package com.flowershop.back.services.impl.email;

import com.flowershop.back.configuration.enums.Messages;
import com.flowershop.back.domain.ReturnResponseBody;
import com.flowershop.back.domain.flower.Flowers;
import com.flowershop.back.domain.flower.MessageDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.exceptions.FlowerNotFoundException;
import com.flowershop.back.exceptions.UserNotFoundException;
import com.flowershop.back.repositories.FlowerRepository;
import com.flowershop.back.repositories.UserRepository;
import com.flowershop.back.services.EmailService;
import com.flowershop.back.services.ReadersService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

import static com.flowershop.back.configuration.UtilsProject.randomHash;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    FlowerRepository flowerRepository;

    @Autowired
    ReadersService readersService;

    @Autowired
    UserRepository userRepository;

    @Value("${api.java.mail.email}")
    private  String emailAdmin;
    @Value("${api.java.mail.password}")
    private  String passwordAdmin;
    @Value("${api.link.address}")
    private String link;



    @Override
    public void sendEmailVerification(String email, String hash) {
        String url = link + "/confirme-email?hash=" + hash;

        String assunto = readersService.usePhrases(Messages.ASSUNTOCONFIRMACAO.getValue());

        String mensagem = String.format(readersService.usePhrases(Messages.MENSAGEMCONFIRMACAO.getValue()), url);

        send(email, assunto, mensagem, null, null);
    }


    @SneakyThrows
    @Override
    public void sendEmailUser(MessageDTO message) {
        userRepository.findByHash(message.hash())
                .orElseThrow(() -> new UserNotFoundException("Usuário não foi encontrado ao enviar o email!"));

        Flowers flower = flowerRepository.findByFilename(message.flower().replace("%20", " "))
                .orElseThrow(() -> new FlowerNotFoundException("Flor não encontrada"));


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(ImageIO.read(new ByteArrayInputStream(flower.getFile())), "jpg", baos);
        Resource imageResource = new ByteArrayResource(baos.toByteArray());


        String assunto = readersService.usePhrases(Messages.ASSUNTO.getValue());
        String mensagemFinal = String.format(readersService.usePhrases(Messages.MENSAGEM.getValue()), flower.getFilename(), message.mensagem());
        send(message.email(), assunto, mensagemFinal, flower.getFilename().concat(".jpg"), imageResource);
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

        User user = userRepository.findByLoginAndHash(email, hash)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        user.setPassword(new BCryptPasswordEncoder().encode(newPass));
        userRepository.save(user);
        String assunto = readersService.usePhrases(Messages.ASSUNTORESETPASS.getValue());
        String mensagemFinal = String.format(readersService.usePhrases(Messages.MENSAGEMRESETSENHA.getValue()), newPass);
        send(email, assunto, mensagemFinal, null, null);
    }



    @Override
    @SneakyThrows
    public ReturnResponseBody send(String email, String assunto, String mensagem, String filename, Resource image)  {
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

                return new ReturnResponseBody("Email enviado com sucesso");
            } catch (MessagingException e) {
                throw new MessagingException("O servidor encontrou uma falha ao tentar enviar o email. Por favor, tente novamente mais tarde.");
            }
    }


}
