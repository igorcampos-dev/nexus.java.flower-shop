package com.flowershop.back.domain.flower;

import com.flowershop.back.configuration.annotations.isValid;

public record MessageDTO (@isValid String email, @isValid String mensagem, @isValid String flower, @isValid String hash) {}
