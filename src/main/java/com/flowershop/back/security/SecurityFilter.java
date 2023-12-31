package com.flowershop.back.security;

import com.flowershop.back.repositories.UserRepository;
import com.flowershop.back.services.TokenService;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import static com.flowershop.back.security.SecurityExceptionMessage.exception;

@Component
public class SecurityFilter extends OncePerRequestFilter {


    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal( @Nullable @NotBlank HttpServletRequest request, @Nullable @NotBlank HttpServletResponse response, @Nullable @NotBlank FilterChain filterChain) throws IOException {
        try {
            assert request != null;
            assert response != null;
            assert filterChain != null;

            recoverToken(Objects.requireNonNull(request))
                    .ifPresent(token -> {
                        var login = tokenService.validateToken(token);
                        userRepository.findByLogin(login)
                                .ifPresent(user -> {
                                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                                    SecurityContextHolder.getContext().setAuthentication(authentication);
                                });
                    });
            filterChain.doFilter(request, response);
        } catch (Exception e) {
                exception(e, request, response);
        }
    }

    private Optional<String> recoverToken(HttpServletRequest request){
       return Optional.ofNullable(request.getHeader("Authorization"))
               .map( authHeader -> authHeader.replace("Bearer", "").strip());
    }
}
