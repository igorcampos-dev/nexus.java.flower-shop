package com.flowershop.back.controllers;

import com.flowershop.back.configuration.ResponseMessage;
import com.flowershop.back.domain.activities.ActivitiesResponseDTO;
import com.flowershop.back.repositories.ActivitiesRepository;
import com.flowershop.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flower-shop")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ActivitiesRepository activitiesRepository;


    @GetMapping("/activities")
    public ResponseEntity activities(@RequestParam("hash") String hash){
        String user = userRepository.findByHash(hash).getLogin();

       if (user == null) return ResponseEntity.badRequest().body(new ResponseMessage("Usuario não existe"));

       List<ActivitiesResponseDTO> activities = this.activitiesRepository.findAll().stream().map(ActivitiesResponseDTO::new).toList();

        return ResponseEntity.ok(activities);
    }



}
