package com.flowershop.back.configuration.enums;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("ADMIN"),

    USER("USER");

    private final String value;

    Role(String value){
        this.value = value;
    }
}
