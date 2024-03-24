package com.nexus.back.repository.operations;

import com.nexus.back.domain.entity.User;

public interface UserDatabaseOperations {
    User findById(String id);
    User findByHash(String hash);
    void loginExists(String login);
    void userExistsByHash(String hash);
    void save(User user);
    void alterStatusUserByHash(String hash);
    User findByLogin(String login);
    User findByLoginAndHash(String email, String hash);
}
