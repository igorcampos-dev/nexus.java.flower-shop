package com.nexus.back.exceptions.object;

import lombok.Builder;

import java.time.Instant;
@Builder
public record Error(Instant timestamp, Integer status, String message, String path) {
}
