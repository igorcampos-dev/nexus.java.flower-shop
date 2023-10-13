package com.flowershop.back.exceptions;

public class UserPendingActivationException extends RuntimeException {
    public UserPendingActivationException(String s) {
        super(s);
    }
}
