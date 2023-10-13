package com.flowershop.back.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Mensagens {

    @Value("${api.link.address}")
    private static String link;

    public static final String ASSUNTO_CONFIRMACAO = "Confirmação de cadastro";
    public static final String MENSAGEM_CONFIRMACAO = "<html><body>Prezado(a),<br/><br/>Esperamos que esteja bem. Estamos entrando em contato para solicitar a confirmação do seu cadastro através do link a seguir:<br/>"
            + "<a href=\"%s\">Clique aqui para confirmar o seu cadastro</a>.<br/><br/>"
            + "Atenciosamente,<br/>"
            + "TI Flower-Shop Campos, link 2:<br/><br/>"
            + "Observação: Esta mensagem é gerada automaticamente e não requer resposta.</body></html>";




    public static final String ASSUNTO = "Uma flor linda para uma pessoa linda \uD83D\uDE0A";
    public static final String LINK_FLOR_TEMPLATE = "<a href=\"%s\">Clique aqui para ver a flor</a>";
    public static final String MENSAGEM_TEMPLATE = "Um usuário que usa nossos serviços mandou uma flor para ti!," +
            "a flor se chama %s, uma linda flor," +
            "também deixou uma mensagem para você:<br/><br/>%s" +
            "<br/><br/>Veja a flor: %s";
    


}


