package com.flowershop.back.services;

import com.flowershop.back.configuration.enums.Role;
import com.flowershop.back.configuration.enums.StatusUser;
import com.flowershop.back.domain.user.AuthenticationDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.exceptions.InvalidCredentialsException;
import com.flowershop.back.exceptions.UserAlreadyExistsException;
import com.flowershop.back.exceptions.UserNotFoundException;
import com.flowershop.back.exceptions.UserPendingActivationException;
import com.flowershop.back.interfaces.InterfaceUserService;
import com.flowershop.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements InterfaceUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void save(User user) {

        userRepository.findByLogin(user.getLogin())
                .ifPresent( p -> {
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
                .map(user -> {
                    if (!passwordEncoder.matches(users.password(), user.getPassword())) {
                        throw new InvalidCredentialsException("Credenciais incorretas!");
                    }
                    if (StatusUser.P.equals(user.getStatus())) {
                        throw new UserPendingActivationException("Usuário está pendente a ativação!");
                    }
                    return user.getHash();
                })
                .orElseThrow( () -> new UserNotFoundException("Usuário não foi encontrado!"));
    }



    @Override
    public User createUser(AuthenticationDTO data, String hash, String pass) {
        return User.builder()
                .login(data.login())
                .hash(hash)
                .password(pass)
                .role(Role.USER)
                .status(StatusUser.P)
                .build();
    }


}

