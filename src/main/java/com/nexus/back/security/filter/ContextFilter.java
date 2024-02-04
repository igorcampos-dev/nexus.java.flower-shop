package com.nexus.back.security.filter;

import com.nexus.security.model.dto.TokenPropertiesDTO;
import com.nexus.security.service.JwtService;
import com.nexus.security.service.SecurityContextInjector;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ContextFilter {

    private final JwtService jwtService;
    private final FilterMethods filterMethods;

    @Bean
    public SecurityContextInjector securityContextInjector() {
        return new SecurityContextInjector() {

            @Override
            public void injectContext(String token) {
                TokenPropertiesDTO login = jwtService.decode(token);
                filterMethods.verifyAndAuthenticateUser(login);
            }
            @Override
            public void exception(Exception e, HttpServletRequest request, HttpServletResponse response) {
               filterMethods.getException(e, request, response);
            }};}
}
