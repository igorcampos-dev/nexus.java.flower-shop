package com.nexus.back.services;

import com.nexus.back.domain.dto.user.AuthenticationDTO;
import com.nexus.back.domain.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
      void save(User user);
      void updateStatus(String hash);
      String getHash(AuthenticationDTO users);
      UserDetails generateUser(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken);
}
