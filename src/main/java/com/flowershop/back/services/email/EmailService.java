package com.flowershop.back.services.email;

import com.flowershop.back.configuration.Mensagens;
import com.flowershop.back.configuration.ResponseMessage;
import com.flowershop.back.configuration.ServerLink;
import com.flowershop.back.domain.flower.MessageDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.exceptions.FlowerNotFoundException;
import com.flowershop.back.exceptions.InvalidEmailException;
import com.flowershop.back.exceptions.UserNotFoundException;
import com.flowershop.back.repositories.FlowerRepository;
import com.flowershop.back.repositories.UserRepository;
import jakarta.mail.MessagingException;
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
import java.util.Optional;
import java.util.Properties;

import static com.flowershop.back.configuration.UtilsProject.isEmailValid;

@Service
@Slf4j
public class EmailService {

    @Autowired
    FlowerRepository repository;

    @Autowired
    UserRepository userRepository;

    @Value("${api.java.mail.email}")
    private  String emailAdmin;
    @Value("${api.java.mail.password}")
    private  String passwordAdmin;
    @Autowired
    private  ServerLink serverLink;




    public void sendEmailVerification(String email, String hash) {
        String assunto = Mensagens.ASSUNTO_CONFIRMACAO;
        String url = serverLink.getLink() + "/confirme-email?hash=" + hash;
        String mensagem = String.format(Mensagens.MENSAGEM_CONFIRMACAO, url);

        send(email, assunto, mensagem);
    }



    public void sendEmailUser(MessageDTO message){

        userRepository.findByHash(message.hash())
                .orElseThrow(() -> new UserNotFoundException("Usuario não foi encontrado ao enviar o email!") );


        repository.findByImage(message.flower())
                .map( flower -> {
                    String assunto = Mensagens.ASSUNTO;
                    String linkFlor = String.format(Mensagens.LINK_FLOR_TEMPLATE, message.flower());
                    String mensagemFinal = String.format(Mensagens.MENSAGEM_TEMPLATE, flower.getName(), message.mensagem(), linkFlor);
                    return send(message.email(), assunto, mensagemFinal);
                })
                .orElseThrow(() -> new FlowerNotFoundException("Flor não encontrada"));
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
    public ResponseMessage send(String email, String assunto, String mensagem) {
        try {
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
                return new ResponseMessage("Email enviado com sucesso!");
            }
        } catch (MessagingException e) {
            throw new MessagingException("O servidor encontrou uma falha ao tentar enviar o email. Por favor, tente novamente mais tarde.");
        }
        throw new InvalidEmailException("Email inválido. Verifique o formato do email fornecido.");
    }
}
