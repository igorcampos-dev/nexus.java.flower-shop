package com.flowershop.back.controllers;

import com.flowershop.back.domain.activities.ActivitiesResponseDTO;
import com.flowershop.back.services.ActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    ActivitiesService activitiesService;


    @GetMapping("/activities")
    public ResponseEntity<List<ActivitiesResponseDTO>> activities(@RequestParam("hash") String hash) {

        List<ActivitiesResponseDTO> activitiesList = this.activitiesService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(activitiesList);

    }
}
