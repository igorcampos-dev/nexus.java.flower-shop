package com.flowershop.back.controllers;

import com.flowershop.back.domain.ReturnResponseBody;
import com.flowershop.back.domain.flower.MessageDTO;
import com.flowershop.back.services.ActivitiesService;
import com.flowershop.back.services.impl.email.EmailServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flower-shop")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Enviar mensagem", description = "Rota relacionada a enviar mensagems para um ente querido")
public class SendmessageController {

    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    ActivitiesService activitiesService;

    @Operation(summary = "envia uma flor para um usuário")
    @PostMapping("/send-message")
    public ResponseEntity<ReturnResponseBody> sendMessage(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Informações para envio de uma flor") @RequestBody @Valid MessageDTO message){
        emailService.sendEmailUser(message);
        this.activitiesService.save(message);
        return ResponseEntity.ok().body(new ReturnResponseBody("Email enviado com sucesso!"));
    }

}
