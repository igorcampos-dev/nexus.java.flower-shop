package com.nexus.back.service.impl;

import com.nexus.back.domain.dto.user.AuthenticationDTO;
import com.nexus.back.domain.entity.User;
import com.nexus.back.domain.enums.Role;
import com.nexus.back.domain.enums.StatusUser;
import com.nexus.back.repository.operations.UserDatabaseOperations;
import com.nexus.back.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDatabaseOperations userDatabaseOperations;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public void save(User user) {
        userDatabaseOperations.loginExists(user.getLogin());
        user.setRole(Role.USER);
        user.setStatus(StatusUser.P);
        userDatabaseOperations.save(user);
    }

    @Override
    public void updateStatus(String hash) {
        userDatabaseOperations.alterStatusUserByHash(hash);
    }

    @Override
    public String getHash(AuthenticationDTO userDTO) {
        User user = userDatabaseOperations.findByLogin(userDTO.login());
        return user.getHash();
    }

    @Override
    public UserDetails generateUser(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return (UserDetails) this.authenticationManager.authenticate(usernamePasswordAuthenticationToken).getPrincipal();
    }


}

