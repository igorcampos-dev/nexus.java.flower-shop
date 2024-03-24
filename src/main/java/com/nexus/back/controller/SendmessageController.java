package com.nexus.back.controller;

import com.nexus.back.domain.Response;
import com.nexus.back.domain.dto.flower.MessageDTO;
import com.nexus.back.service.ActivitiesService;
import com.nexus.back.service.impl.email.EmailServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/shop")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Enviar mensagem", description = "Rota relacionada a enviar mensagems para um ente querido")
public class SendmessageController {

    private final EmailServiceImpl emailService;
    private final ActivitiesService activitiesService;

    @Operation(summary = "envia uma flor para um usuário")
    @PostMapping("/send-message")
    public ResponseEntity<Response> sendMessage(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Informações para envio de uma flor") @RequestBody @Valid MessageDTO message){
        emailService.sendEmailUser(message);
        this.activitiesService.save(message);
        return ResponseEntity.ok()
                             .body(RESPONSE);
    }

    private static final Response RESPONSE = new Response("Email enviado com sucesso!");

}
