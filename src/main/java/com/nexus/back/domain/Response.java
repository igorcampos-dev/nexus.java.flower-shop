package com.nexus.back.domain;

import com.nexus.validations.NonNullOrBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public record Response(
        @Schema(example = "Mensagem de retorno!",
                description = "Retorno das mensagens")
        @NonNullOrBlank
        String message) {
}
