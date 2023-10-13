package com.flowershop.back.controllers;

import com.flowershop.back.configuration.ResponseMessage;
import com.flowershop.back.domain.flower.MessageDTO;
import com.flowershop.back.services.ActivitiesService;
import com.flowershop.back.services.email.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flower-shop")
public class SendmessageController {

    @Autowired
    EmailService emailService;

    @Autowired
    ActivitiesService activitiesService;

    @PostMapping("/send-message")
    public ResponseEntity<ResponseMessage> sendMessage(@RequestBody @Valid MessageDTO message){

        emailService.sendEmailUser(message);

        this.activitiesService.save(message);

        return ResponseEntity.ok().body(new ResponseMessage("Email enviado com sucesso!"));
    }

}
