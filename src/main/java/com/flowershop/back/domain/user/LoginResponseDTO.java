package com.flowershop.back.domain.user;

import com.flowershop.back.configuration.annotations.isValid;

public record LoginResponseDTO(@isValid String token,
                               String hash) {
}
