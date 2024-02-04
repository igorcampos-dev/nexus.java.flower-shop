package com.nexus.back.domain.dto.user;

import com.nexus.back.domain.entity.User;
import com.nexus.back.domain.enums.Role;
import com.nexus.back.domain.enums.StatusUser;
import lombok.Builder;

@Builder
public record UserDTO(StatusUser status, Role role, String login, String hash, String password) {
    public User toModel() {
        return User.builder()
                .status(this.status)
                .role(this.role)
                .login(this.login)
                .hash(this.hash)
                .password(this.password)
                .build();
    }
}
