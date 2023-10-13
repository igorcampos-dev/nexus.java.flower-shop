package com.flowershop.back.exceptions.object;

import lombok.Builder;

import java.time.Instant;
@Builder
public record Error(Instant timestamp, Integer status, String error, String message, String path) {
}
