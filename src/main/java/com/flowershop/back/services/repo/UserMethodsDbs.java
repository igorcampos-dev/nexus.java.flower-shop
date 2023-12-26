package com.flowershop.back.services.repo;

import com.flowershop.back.domain.user.User;

public interface UserMethodsDbs {
    User findById(String id);
    User findByHash(String hash);
    void loginExists(String login);
    void userExistsByHash(String hash);
    void save(User user);
    void alterStatusUserByHash(String hash);
    User findByLogin(String login);
    User findByLoginAndHash(String email, String hash);
}
