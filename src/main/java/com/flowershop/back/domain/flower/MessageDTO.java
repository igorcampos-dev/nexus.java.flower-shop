package com.flowershop.back.domain.flower;

import com.flowershop.back.configuration.annotations.isValid;
import jakarta.validation.constraints.Email;

public record MessageDTO (@isValid @Email String email,
                          @isValid String mensagem,
                          @isValid String flower,
                          @isValid String hash) {}
