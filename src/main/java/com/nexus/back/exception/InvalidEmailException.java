package com.nexus.back.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String s) {
        super(s);
    }
}
