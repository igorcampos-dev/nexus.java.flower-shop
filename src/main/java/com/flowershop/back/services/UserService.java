package com.flowershop.back.services;

import com.flowershop.back.interfaces.InterfaceUserService;
import com.flowershop.back.configuration.enums.Role;
import com.flowershop.back.configuration.enums.StatusUser;
import com.flowershop.back.domain.user.AuthenticationDTO;
import com.flowershop.back.domain.user.RegisterDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.exceptions.UserAlreadyExistsException;
import com.flowershop.back.exceptions.UserPendingActivationException;
import com.flowershop.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements InterfaceUserService {

    @Autowired
    UserRepository userRepository;


    @Override
    public void save(User user) {

        userRepository.findByLogin(user.getLogin())
                .ifPresent(existingUser -> {
                    throw new UserAlreadyExistsException("Já existe um Usuário com certas informações. Por favor, escolha credenciais diferentes.");
                });

        userRepository.save(user);

    }

    @Override
    public void updateStatus(String hash) {

        userRepository.findByHash(hash)
                .filter(user -> user.getStatus() == StatusUser.P)
                .ifPresent(user -> {
                    user.setStatus(StatusUser.A);
                    userRepository.save(user);
                });

    }

    @Override
    public String validateUser(AuthenticationDTO users) {
        return userRepository.findByLogin(users.login())
                .filter(user -> StatusUser.A.equals(user.getStatus()))
                .map(User::getHash)
                .orElseThrow(() -> new UserPendingActivationException("Usuário está pendente a ativação!"));

    }

    @Override
    public User createUser(RegisterDTO data, String hash, String pass) {
        return User.builder()
                .login(data.login())
                .hash(hash)
                .password(pass)
                .role(Role.USER)
                .status(StatusUser.P)
                .build();
    }


}

