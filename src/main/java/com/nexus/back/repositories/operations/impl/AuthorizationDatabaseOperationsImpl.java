package com.nexus.back.repositories.operations.impl;

import com.nexus.back.repositories.UserRepository;
import com.nexus.back.repositories.operations.AuthorizationDatabaseOperations;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationDatabaseOperationsImpl implements AuthorizationDatabaseOperations {

    private final UserRepository repository;

    @Override
    public User findByLogin(String username) {
        return repository.findByLogin(username)
                .map(user -> new User(
                        user.getLogin(), user.getPassword(), user.getAuthorities()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }
}
