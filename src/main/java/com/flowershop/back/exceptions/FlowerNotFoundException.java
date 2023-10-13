package com.flowershop.back.exceptions;

public class FlowerNotFoundException extends RuntimeException {
    public FlowerNotFoundException(String s) {
        super(s);
    }
}
