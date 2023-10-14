package com.flowershop.back.security;

import com.flowershop.back.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       recoverToken(request)
               .ifPresent( token -> {
                   var login = tokenService.validateToken(token);
                   userRepository.findByLogin(login)
                           .ifPresent( user -> {
                               var authentication = new UsernamePasswordAuthenticationToken( user, null, user.getAuthorities());
                               SecurityContextHolder.getContext().setAuthentication(authentication);
                           });
               });
        filterChain.doFilter(request, response);
    }

    private Optional<String> recoverToken(HttpServletRequest request){
       return Optional.ofNullable(request.getHeader("Authorization"))
               .map( authHeader -> authHeader.replace("Bearer", "").strip());
    }
}
