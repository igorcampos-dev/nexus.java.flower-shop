package com.flowershop.back.exceptions;

public class FlowerAlreadyExistsException extends RuntimeException {
    public FlowerAlreadyExistsException(String s) {
        super(s);
    }
}
