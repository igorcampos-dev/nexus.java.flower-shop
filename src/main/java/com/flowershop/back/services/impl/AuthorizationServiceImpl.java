package com.flowershop.back.services.impl;

import com.flowershop.back.services.repo.AuthorizationMethodsDbs;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements UserDetailsService {

    private final AuthorizationMethodsDbs authorizationMethodsDbs;

    public AuthorizationServiceImpl(AuthorizationMethodsDbs authorizationMethodsDbs) {
        this.authorizationMethodsDbs = authorizationMethodsDbs;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authorizationMethodsDbs.findByLogin(username);
    }

}
