package com.flowershop.back.services.impl.email;

import com.flowershop.back.configuration.enums.Messages;
import com.flowershop.back.domain.ReturnResponseBody;
import com.flowershop.back.domain.flower.Flowers;
import com.flowershop.back.domain.flower.MessageDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.exceptions.FlowerNotFoundException;
import com.flowershop.back.exceptions.InvalidEmailException;
import com.flowershop.back.exceptions.UserNotFoundException;
import com.flowershop.back.repositories.FlowerRepository;
import com.flowershop.back.repositories.UserRepository;
import com.flowershop.back.services.EmailService;
import com.flowershop.back.services.ReadersService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Properties;


@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    FlowerRepository repository;

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

        String assunto = readersService.fileHtmlConfirmacao(Messages.ASSUNTOCONFIRMACAO.getValue());

        String mensagem = String.format(readersService.fileHtmlConfirmacao(Messages.MENSAGEMCONFIRMACAO.getValue()), url);

        send(email, assunto, mensagem);
    }


    @Override
    public void sendEmailUser(MessageDTO message) {

        userRepository.findByHash(message.hash())
                .orElseThrow(() -> new UserNotFoundException("Usuário não foi encontrado ao enviar o email!"));

        Flowers flower = repository.findByImage(message.flower())
                .orElseThrow(() -> new FlowerNotFoundException("Flor não encontrada"));


        String assunto = readersService.fileSendEmail(Messages.ASSUNTO.getValue());
        String linkFlor = String.format(readersService.fileSendEmail(Messages.LINKFLOR.getValue()), message.flower());
        String mensagemFinal = String.format(readersService.fileSendEmail(Messages.MENSAGEM.getValue()), flower.getName(), message.mensagem(), linkFlor);


        send(message.email(), assunto, mensagemFinal);
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
    @SneakyThrows
    public ReturnResponseBody send(String email, String assunto, String mensagem)  {
        if (EmailValidator.getInstance().isValid(email)) {
            try {
                JavaMailSender emailSender = MailSender();
                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setFrom(emailAdmin);
                helper.setTo(email.toLowerCase());
                helper.setSubject(assunto);
                helper.setSentDate(new Date());
                helper.setText(mensagem, true);
                emailSender.send(message);

                return new ReturnResponseBody("Email enviado com sucesso");
            } catch (MessagingException e) {
                throw new MessagingException("O servidor encontrou uma falha ao tentar enviar o email. Por favor, tente novamente mais tarde.");
            }
        } else {
            throw new InvalidEmailException("Email inválido. Verifique o formato do email fornecido.");
        }
    }


}
