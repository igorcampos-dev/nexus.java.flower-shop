package com.flowershop.back.services;

import com.flowershop.back.configuration.enums.StatusUser;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    public boolean updateStatus(String token){
        User user = userRepository.findByHash(token);

        if (user == null){ return false; }
            user.setStatus(StatusUser.A);
            userRepository.save(user);
            return true;
        }


    }

