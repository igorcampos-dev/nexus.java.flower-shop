package com.flowershop.back.configuration.enums;

import lombok.Getter;

@Getter
public enum Messages {

    ASSUNTO("Assunto.html"),

    ASSUNTOCONFIRMACAO("Assunto_Confirmacao.html"),

    LINKFLOR("Link_Flor.html"),

    MENSAGEM("Mensagem.html"),

    MENSAGEMCONFIRMACAO("Mensagem_Confirmacao.html"),

    EMAILFALHA("emailFalha.html"),

    EMAILSUCESSO("emailSucesso.html");


    private final String value;
    Messages(String value) {
        this.value = value;
    }
}
