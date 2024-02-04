package com.nexus.back.domain.dto.flower;

import com.nexus.validations.NonNullOrBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

public record MessageDTO (

        @Schema(description = "Email do usuário",
                example = "exemploUsuario@example.com")
        @NonNullOrBlank
        @Email
        String email,

        @Schema(description = "Mensagem que você queira enviar por email",
                example = "Oi, tudo bem, quero te dar uma flor, demostrada pela minha admiração por você!")
        @NonNullOrBlank
        String mensagem,

        @Schema(description = "Nome da flor existente",
                example = "Tulipa")
        @NonNullOrBlank
        String flower,

        @Schema(description = "Hash do usuário",
                example = "HvxZtx6R6JPntroYQ1dgo4281hgwB3p7zoM1yzX3mfyWHdVK3G")
        @NonNullOrBlank
        String hash
) {}
