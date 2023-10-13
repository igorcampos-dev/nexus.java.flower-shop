package com.flowershop.back.exceptions;

import com.auth0.jwt.exceptions.JWTCreationException;

public class TokenErrorException extends RuntimeException {
    public TokenErrorException(String errorWhileGeneratingToken, JWTCreationException exception) {
        super(errorWhileGeneratingToken, exception);
    }
}
