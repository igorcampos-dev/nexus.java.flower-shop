package com.nexus.back.domain.dto.user;

import com.nexus.validations.NonNullOrBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

public record AuthenticationDTO(

        @Schema(example = "exemploUsuario@example.com",
                description = "Email do usuário")
        @NonNullOrBlank
        @Email(message = "Deve ser um endereço de e-mail válido")
        String login,

        @Schema(example = "exemploSenha123",
                description = "Senha do usuário")
        @NonNullOrBlank(message = "A senha não pode estar em branco")
        String password) {
}

