package com.flowershop.back.domain.user;

import com.flowershop.back.configuration.annotations.isValid;
import jakarta.validation.constraints.Email;

public record AuthenticationDTO(@isValid @Email String login,
                                @isValid String password) {
}
