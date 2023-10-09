package com.flowershop.back.domain.user;

import com.flowershop.back.configuration.annotations.isValid;

public record AuthenticationDTO(@isValid String login, @isValid String password) {
}
