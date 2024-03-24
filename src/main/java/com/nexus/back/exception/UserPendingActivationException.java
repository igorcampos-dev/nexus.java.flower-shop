package com.nexus.back.exception;

public class UserPendingActivationException extends RuntimeException {
    public UserPendingActivationException(String s) {
        super(s);
    }
}
