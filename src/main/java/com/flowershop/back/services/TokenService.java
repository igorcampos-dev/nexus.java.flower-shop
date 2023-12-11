package com.flowershop.back.services;

import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {

    String generateToken(UserDetails user);
    String validateToken(String token);
    String generateShortToken();
    boolean isShortTokenExpired(String token);
}
