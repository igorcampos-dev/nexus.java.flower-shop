package com.nexus.back.service;

import com.nexus.back.domain.dto.flower.MessageDTO;

public interface EmailService {

    void sendEmailVerification(String email, String hash);
    void sendEmailUser(MessageDTO message);
    void sendEmailResetPass(String email, String hash);
}
