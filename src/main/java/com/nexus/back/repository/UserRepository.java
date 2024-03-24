package com.nexus.back.repository;

import com.nexus.back.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByHash(String hash);
    Optional<User> findByLogin(String email);
    Optional<User> findByLoginAndHash(String login, String hash);

}
