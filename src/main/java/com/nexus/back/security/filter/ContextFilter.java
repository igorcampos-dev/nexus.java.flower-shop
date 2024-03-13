package com.nexus.back.security.filter;

import com.nexus.security.properties.TokenProperties;
import com.nexus.security.service.filter.SecurityContextInjector;
import com.nexus.security.service.jwt.JwtService;
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
                TokenProperties login = jwtService.decode(token);
                filterMethods.verifyAndAuthenticateUser(login);
            }
            };}
}
