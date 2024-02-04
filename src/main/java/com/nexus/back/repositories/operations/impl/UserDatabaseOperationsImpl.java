package com.nexus.back.repositories.operations.impl;

import com.nexus.back.domain.entity.User;
import com.nexus.back.domain.enums.StatusUser;
import com.nexus.back.exceptions.UserAlreadyExistsException;
import com.nexus.back.exceptions.UserNotFoundException;
import com.nexus.back.repositories.UserRepository;
import com.nexus.back.repositories.operations.UserDatabaseOperations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDatabaseOperationsImpl implements UserDatabaseOperations {

    private final UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(String id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não existe"));
    }

    @Override
    public User findByHash(String hash){
        return userRepository.findByHash(hash).orElseThrow(() -> new UserNotFoundException("Usuário não foi encontrado ao salvar a sua atividade"));
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("Usuário não existe."));
    }

    @Override
    public User findByLoginAndHash(String email, String hash) {
        return userRepository.findByLoginAndHash(email, hash).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    @Override
    public void loginExists(String login) {
        userRepository.findByLogin(login).ifPresent(s -> {
            throw new UserAlreadyExistsException("Já existe um Usuário com certas informações. Por favor, escolha credenciais diferentes.");
        });
    }

    @Override
    public void userExistsByHash(String hash) {
        if (userRepository.findByHash(hash).isEmpty()) {
            throw new UserNotFoundException("Usuário não encontrado para o hash: ");
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
