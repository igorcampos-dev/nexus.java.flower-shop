package com.flowershop.back.configuration.enums;

import lombok.Getter;

@Getter
public enum Messages {

    MENSAGEM("p1"),
    ASSUNTOCONFIRMACAO("p2"),
    MENSAGEMCONFIRMACAO("p3"),
    ASSUNTO("p4"),
    LINKFLOR("p5"),
    MENSAGEMRESETSENHA("p6"),
    ASSUNTORESETPASS("p7"),
    EMAILFALHA("emailFalha.html"),
    EMAILSUCESSO("emailSucesso.html");


    private final String value;
    Messages(String value) {
        this.value = value;
    }
}
