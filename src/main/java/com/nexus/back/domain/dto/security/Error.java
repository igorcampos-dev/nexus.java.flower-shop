package com.nexus.back.domain.dto.security;

import lombok.Builder;

@Builder
public record Error(String timestamp, Integer status, String error, String message, String path) {}
