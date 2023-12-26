package com.flowershop.back.services.repo;

import org.springframework.security.core.userdetails.User;

public interface AuthorizationMethodsDbs {
    User findByLogin(String username);
}
