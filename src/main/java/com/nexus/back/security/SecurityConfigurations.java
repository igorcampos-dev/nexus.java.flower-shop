package com.nexus.back.security;

import com.nexus.security.dto.Routes;
import com.nexus.security.service.filter.FilterService;
import com.nexus.security.service.filter.SecurityContextInjector;
import com.nexus.security.service.routes.RoutesService;
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

    private List<Routes> routes;
    private List<Routes> routesAdmin;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, SecurityContextInjector securityContextInjector) throws Exception {
        routes = List.of(new Routes("/shop/register", HttpMethod.POST),
                         new Routes("/shop/login", HttpMethod.POST),
                         new Routes("/shop/confirme-email", HttpMethod.GET),
                         new Routes("/api/v1/auth/**", HttpMethod.GET),
                         new Routes("/v3/api-docs/**", HttpMethod.GET),
                         new Routes("/v3/api-docs.yaml", HttpMethod.GET),
                         new Routes("/swagger-ui/**", HttpMethod.GET),
                         new Routes("/swagger-ui.html", HttpMethod.GET));

        routesAdmin = List.of(new Routes("/shop/register-flower/{filename}", HttpMethod.POST),
                              new Routes("/shop/see-flowers/{filename}", HttpMethod.GET),
                              new Routes("/shop/delete/{id}", HttpMethod.DELETE),
                              new Routes("/shop/update-flower/{id}/{filename}", HttpMethod.PUT));

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
