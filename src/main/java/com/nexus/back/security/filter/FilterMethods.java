package com.nexus.back.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nexus.back.domain.dto.security.Error;
import com.nexus.back.repositories.UserRepository;
import com.nexus.security.model.dto.TokenPropertiesDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@AllArgsConstructor
@Component
public class FilterMethods {

    private final UserRepository userRepository;


    protected void verifyAndAuthenticateUser(TokenPropertiesDTO login){
        userRepository.findByLogin(login.email())
                .ifPresent(user -> {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });
    }

    @SneakyThrows
    protected void getException(Exception e, HttpServletRequest request, HttpServletResponse response){
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.writeValue(response.getWriter(), createError(e, request));

    }
    
    public Error createError(Exception e, HttpServletRequest request){
        return Error.builder()
                .timestamp(Instant.now().toString())
                .message(e.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(request.getRequestURI())
                .build();
    }
}
