package com.flowershop.back.configuration.enums;

public enum Role {

    ADMIN("ADMIN"),

    USER("USER");

    private String permitions;

    Role(String permitions){
        this.permitions = permitions;
    }
}
