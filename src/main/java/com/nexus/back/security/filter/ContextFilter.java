package com.nexus.back.security.filter;

import com.nexus.security.model.dto.TokenPropertiesDTO;
import com.nexus.security.service.filter.SecurityContextInjector;
import com.nexus.security.service.jwt.JwtService;
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
            };}
}
