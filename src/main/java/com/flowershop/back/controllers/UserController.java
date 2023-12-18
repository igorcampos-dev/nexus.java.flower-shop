package com.flowershop.back.controllers;

import com.flowershop.back.domain.activities.ActivitiesResponseDTO;
import com.flowershop.back.services.ActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flower-shop")
public class UserController {
    @Autowired
    ActivitiesService activitiesService;


    @GetMapping("/activities/{id}")
    public ResponseEntity<List<ActivitiesResponseDTO>> activities(@PathVariable("id") String id) {
        List<ActivitiesResponseDTO> activitiesList = this.activitiesService.findAllById(id);
        return ResponseEntity.status(HttpStatus.OK).body(activitiesList);

    }
}
