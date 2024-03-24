package com.nexus.back.repository.operations.impl;

import com.nexus.back.domain.entity.User;
import com.nexus.back.domain.enums.StatusUser;
import com.nexus.back.exception.UserAlreadyExistsException;
import com.nexus.back.exception.UserNotFoundException;
import com.nexus.back.repository.UserRepository;
import com.nexus.back.repository.operations.UserDatabaseOperations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDatabaseOperationsImpl implements UserDatabaseOperations {

    private final UserRepository userRepository;
    private static final UserNotFoundException USER_NOT_FOUND_EXCEPTION = new UserNotFoundException("Usuário não existe");
    private static final UserAlreadyExistsException USER_ALREADY_EXISTS_EXCEPTION = new UserAlreadyExistsException("Já existe um Usuário com certas informações. Por favor, escolha credenciais diferentes.");

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(String id){
        return userRepository.findById(id).orElseThrow(() -> USER_NOT_FOUND_EXCEPTION);
    }

    @Override
    public User findByHash(String hash){
        return userRepository.findByHash(hash).orElseThrow(() -> USER_NOT_FOUND_EXCEPTION);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> USER_NOT_FOUND_EXCEPTION);
    }

    @Override
    public User findByLoginAndHash(String email, String hash) {
        return userRepository.findByLoginAndHash(email, hash).orElseThrow(() -> USER_NOT_FOUND_EXCEPTION);
    }

    @Override
    public void loginExists(String login) {
        userRepository.findByLogin(login).ifPresent(s -> {
            throw USER_ALREADY_EXISTS_EXCEPTION;
        });
    }

    @Override
    public void userExistsByHash(String hash) {
        if (userRepository.findByHash(hash).isEmpty()) {
            throw USER_NOT_FOUND_EXCEPTION;
        }
    }

    @Override
    public void alterStatusUserByHash(String hash) {
        userRepository.findByHash(hash)
                .filter(user -> user.getStatus() == StatusUser.P)
                .ifPresent(user -> {
                    user.setStatus(StatusUser.A);
                    userRepository.save(user);
                });
    }
}
