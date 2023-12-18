package com.flowershop.back.domain;

import com.flowershop.back.configuration.annotations.isValid;
public record ReturnResponseBody(@isValid String message) {
}
