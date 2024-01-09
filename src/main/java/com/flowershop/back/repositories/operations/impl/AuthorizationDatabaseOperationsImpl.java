package com.flowershop.back.repositories.operations.impl;

import com.flowershop.back.repositories.UserRepository;
import com.flowershop.back.repositories.operations.AuthorizationDatabaseOperations;
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
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getLogin(), user.getPassword(), user.getAuthorities()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }
}
