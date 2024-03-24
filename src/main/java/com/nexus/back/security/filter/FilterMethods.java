package com.nexus.back.security.filter;

import com.nexus.back.repository.UserRepository;
import com.nexus.security.properties.TokenProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class FilterMethods {

    private final UserRepository userRepository;

    protected void verifyAndAuthenticateUser(TokenProperties login){
        userRepository.findByLogin(login.username())
                .ifPresent(user -> {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });
    }
}
