package com.flowershop.back.services;

import com.flowershop.back.domain.user.AuthenticationDTO;
import com.flowershop.back.domain.user.User;

public interface UserService {
      void save(User user);
      void updateStatus(String hash);
      String validateUser(AuthenticationDTO users);
      User createUser(AuthenticationDTO data, String hash, String pass);
      void resetPassword(String hash, String pass, String key);

}
