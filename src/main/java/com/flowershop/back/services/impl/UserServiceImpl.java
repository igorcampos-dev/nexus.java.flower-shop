package com.flowershop.back.services.impl;

import com.flowershop.back.configuration.enums.Role;
import com.flowershop.back.configuration.enums.StatusUser;
import com.flowershop.back.domain.user.AuthenticationDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.exceptions.InvalidCredentialsException;
import com.flowershop.back.exceptions.UserPendingActivationException;
import com.flowershop.back.services.UserService;
import com.flowershop.back.services.repo.UserMethodsDbs;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMethodsDbs userMethodsDbs;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMethodsDbs userMethodsDbs, PasswordEncoder passwordEncoder) {
        this.userMethodsDbs = userMethodsDbs;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(User user) {
        userMethodsDbs.loginExists(user.getLogin());
        user.setRole(Role.USER);
        user.setStatus(StatusUser.P);
        userMethodsDbs.save(user);
    }

    @Override
    public void updateStatus(String hash) {
        userMethodsDbs.alterStatusUserByHash(hash);
    }

    @Override
    public String validateUser(AuthenticationDTO users) {

        User user = userMethodsDbs.findByLogin(users.login());

        if (!passwordEncoder.matches(users.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Credenciais incorretas!");
        }
        if (StatusUser.P.equals(user.getStatus())) {
            throw new UserPendingActivationException("Usuário está pendente a ativação!");
        }
        return user.getHash();
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

