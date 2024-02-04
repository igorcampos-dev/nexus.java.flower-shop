package com.nexus.back.services.impl;

import com.nexus.back.repositories.operations.AuthorizationDatabaseOperations;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationServiceImpl implements UserDetailsService {

    private final AuthorizationDatabaseOperations authorizationDatabaseOperations;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authorizationDatabaseOperations.findByLogin(username);
    }

}
