package com.flowershop.back.exceptions;

public class TokenExpirationException extends RuntimeException {
    public TokenExpirationException(String s) {
        super(s);
    }
}
