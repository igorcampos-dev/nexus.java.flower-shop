package com.flowershop.back.services;

import com.flowershop.back.domain.ReturnResponseBody;
import com.flowershop.back.domain.flower.MessageDTO;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;

public interface EmailService {

    void sendEmailVerification(String email, String hash);
    void sendEmailUser(MessageDTO message);
    JavaMailSender MailSender();
    void sendEmailResetPass(String email, String hash);
    ReturnResponseBody send(String email, String assunto, String mensagem, String filename, Resource image);
}
