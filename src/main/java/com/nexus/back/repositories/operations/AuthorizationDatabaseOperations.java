package com.nexus.back.repositories.operations;

import org.springframework.security.core.userdetails.User;

public interface AuthorizationDatabaseOperations {
    User findByLogin(String username);
}