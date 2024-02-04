package com.nexus.back.security;

import com.nexus.security.model.dto.RoutesDTO;
import com.nexus.security.service.FilterService;
import com.nexus.security.service.RoutesService;
import com.nexus.security.service.SecurityContextInjector;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfigurations {

    private List<RoutesDTO> routes;
    private List<RoutesDTO> routesAdmin;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, SecurityContextInjector securityContextInjector) throws Exception {
        routes = List.of(new RoutesDTO("/shop/register", HttpMethod.POST),
                         new RoutesDTO("/shop/login", HttpMethod.POST),
                         new RoutesDTO("/shop/confirme-email", HttpMethod.GET),
                         new RoutesDTO("/api/v1/auth/**", HttpMethod.GET),
                         new RoutesDTO("/v3/api-docs/**", HttpMethod.GET),
                         new RoutesDTO("/v3/api-docs.yaml", HttpMethod.GET),
                         new RoutesDTO("/swagger-ui/**", HttpMethod.GET),
                         new RoutesDTO("/swagger-ui.html", HttpMethod.GET));

        routesAdmin = List.of(new RoutesDTO("/shop/register-flower/{filename}", HttpMethod.POST),
                              new RoutesDTO("/shop/see-flowers/{filename}", HttpMethod.GET),
                              new RoutesDTO("/shop/delete/{id}", HttpMethod.DELETE),
                              new RoutesDTO("/shop/update-flower/{id}/{filename}", HttpMethod.PUT));

        return new RoutesService(httpSecurity,
                                 routes,
                                 routesAdmin,
                                 new FilterService(securityContextInjector)).configure();
    }

    @Bean
    public OncePerRequestFilter customFilter(SecurityContextInjector contextInjector) {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
                new FilterService(contextInjector).doFilter(request, response, filterChain);
            }};}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
