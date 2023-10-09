package com.flowershop.back.controllers;

import com.flowershop.back.configuration.ResponseMessage;
import com.flowershop.back.domain.activities.Activities;
import com.flowershop.back.domain.activities.ActivitiesDTO;
import com.flowershop.back.domain.flower.Flowers;
import com.flowershop.back.domain.flower.MessageDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.repositories.ActivitiesRepository;
import com.flowershop.back.repositories.FlowerRepository;
import com.flowershop.back.repositories.UserRepository;
import com.flowershop.back.services.email.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/flower-shop")
public class SendmessageController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    ActivitiesRepository  activitiesRepository;

    @Autowired
    FlowerRepository repository;

    @PostMapping("/send-message")
    public ResponseEntity<ResponseMessage> sendMessage(@RequestBody @Valid MessageDTO userMessage){
        User user = userRepository.findByHash(userMessage.hash());
        Flowers flowers = repository.findByImage(userMessage.flower());

        if ( user == null) return ResponseEntity.badRequest().body(new ResponseMessage("Não foi possivel encontrar o usuario!"));
        if (!emailService.sendEmailUser(userMessage)) return ResponseEntity.badRequest().body(new ResponseMessage("Não foi possivel enviar o email!"));
        if (flowers == null) return ResponseEntity.badRequest().body(new ResponseMessage("flor inexistente!"));

        ActivitiesDTO activitiesDTO = new ActivitiesDTO(user.getLogin(), userMessage.email(), LocalDateTime.now());
        Activities activities = new Activities(activitiesDTO);

        activitiesRepository.save(activities);

        return ResponseEntity.ok().body(new ResponseMessage("Email enviado com sucesso!"));
    }



}
