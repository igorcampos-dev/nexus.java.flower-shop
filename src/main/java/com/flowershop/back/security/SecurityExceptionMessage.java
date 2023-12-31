package com.flowershop.back.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.time.Instant;
public class SecurityExceptionMessage {

    static ObjectMapper exception(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Error error = Error.builder()
                .timestamp(Instant.now().toString())
                .message(e.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(request.getRequestURI())
                .build();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.writeValue(response.getWriter(), error);

        return objectMapper;
    }

    @Builder
    record Error(String timestamp, Integer status, String error, String message, String path) {
    }
}
