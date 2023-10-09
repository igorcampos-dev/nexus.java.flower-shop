package com.flowershop.back.services.email;

import com.flowershop.back.configuration.ServerLink;
import com.flowershop.back.domain.flower.Flowers;
import com.flowershop.back.domain.flower.MessageDTO;
import com.flowershop.back.repositories.FlowerRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Properties;

import static com.flowershop.back.configuration.Utils.isEmailValid;

@Service
@Slf4j
public class EmailService {

    @Value("${api.java.mail.email}")
    private String emailAdmin;
    @Value("${api.java.mail.password}")
    private String passwordAdmin;
    @Autowired
    private ServerLink serverLink;

    @Autowired
    FlowerRepository repository;
    public boolean sendEmailVerification(String email, String hash) {
        String assunto = "Confirmação de cadastro";
        String url = serverLink.getLink() + "/confirme-email?hash=" + hash;
        String mensagem = "<html><body>Prezado(a),<br/><br/>Esperamos que esteja bem. Estamos entrando em contato para solicitar a confirmação do seu cadastro através do link a seguir:<br/>"
                + "<a href=\"" + url + "\">Clique aqui para confirmar o seu cadastro</a>.<br/><br/>"
                + "Atenciosamente,<br/>"
                + "TI Flower-Shop Campos, link 2:<br/><br/>"
                + "Observação: Esta mensagem é gerada automaticamente e não requer resposta.</body></html>";


        return send(email, assunto, mensagem);
    }

    public boolean sendEmailUser(MessageDTO userMessage){
        Flowers flower = repository.findByImage(userMessage.flower());

        String assunto = "Uma flor linda para uma pessoa linda \uD83D\uDE0A";
        String linkFlor = "<a href=\"" + userMessage.flower() + "\">Clique aqui para ver a flor</a>";
        String mensagemFinal = "Um usuário que usa nossos serviços mandou uma flor para ti!, " +
                "a flor se chama " + flower.getName() + ", uma linda flor, " +
                "também deixou uma mensagem para você:<br/><br/>" + userMessage.mensagem() +
                "<br/><br/>Veja a flor: " + linkFlor;

        return send(userMessage.email(), assunto, mensagemFinal);

    }


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

    @SneakyThrows
    public boolean send(String email, String assunto, String mensagem){
        if (isEmailValid(email)) {
            JavaMailSender emailSender = MailSender();
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(emailAdmin);
            helper.setTo(email.toLowerCase());
            helper.setSubject(assunto);
            helper.setSentDate(new Date());
            helper.setText(mensagem, true);

            emailSender.send(message);
            return true;
        }
        return false;
    }
}
