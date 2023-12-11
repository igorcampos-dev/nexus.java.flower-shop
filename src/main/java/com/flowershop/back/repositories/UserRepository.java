package com.flowershop.back.repositories;

import com.flowershop.back.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByHash(String hash);
    Optional<User> findByLogin(String email);

}
