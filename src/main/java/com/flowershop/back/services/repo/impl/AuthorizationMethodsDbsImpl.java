package com.flowershop.back.services.repo.impl;

import com.flowershop.back.repositories.UserRepository;
import com.flowershop.back.services.repo.AuthorizationMethodsDbs;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationMethodsDbsImpl implements AuthorizationMethodsDbs {

    private final UserRepository repository;

    public AuthorizationMethodsDbsImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findByLogin(String username) {
        return repository.findByLogin(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getLogin(), user.getPassword(), user.getAuthorities()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }
}
