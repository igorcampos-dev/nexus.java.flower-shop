package com.flowershop.back.interfaces;

import com.flowershop.back.domain.user.AuthenticationDTO;
import com.flowershop.back.domain.user.RegisterDTO;
import com.flowershop.back.domain.user.User;

public interface InterfaceUserService {
      void save(User user);
      void updateStatus(String hash);
      String validateUser(AuthenticationDTO users);
      User createUser(RegisterDTO data, String hash, String pass);

}
