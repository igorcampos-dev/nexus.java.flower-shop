package com.flowershop.back.domain;

import com.flowershop.back.configuration.annotations.isValid;
import io.swagger.v3.oas.annotations.media.Schema;

public record ReturnResponseBody(
        @Schema(example = "Mensagem de retorno!",
                description = "Retorno das mensagens")
        @isValid
        String message) {
}
