package com.flowershop.back.interfaces;

import com.flowershop.back.domain.ReturnResponseBody;
import com.flowershop.back.domain.flower.MessageDTO;
import org.springframework.mail.javamail.JavaMailSender;

public interface InterfaceEmailService {

    void sendEmailVerification(String email, String hash);
    void sendEmailUser(MessageDTO message);
    JavaMailSender MailSender();
    ReturnResponseBody send(String email, String assunto, String mensagem);
}
