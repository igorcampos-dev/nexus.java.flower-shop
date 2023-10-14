package com.flowershop.back.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface InterfacesTokenService {

    String generateToken(UserDetails user);
    String validateToken(String token);
}
