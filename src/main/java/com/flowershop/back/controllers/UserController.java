package com.flowershop.back.controllers;

import com.flowershop.back.domain.activities.ActivitiesResponseDTO;
import com.flowershop.back.services.ActivitiesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/flower-shop")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Ver atividades", description = "Relacionada a atividades do usuário no processo de envio de emails")
public class UserController {

    private final ActivitiesService activitiesService;

    @Operation(summary = "Ver atividades de um usuário com o seu id")
    @GetMapping("/activities/{id}")
    public ResponseEntity<List<ActivitiesResponseDTO>> activities(@Schema(description = "id do usuário", example = "1320ec46-8dc4-4874-a191-8f195703376c") @PathVariable("id") String id) {
        List<ActivitiesResponseDTO> activitiesList = this.activitiesService.findAllById(id);
        return ResponseEntity.status(HttpStatus.OK).body(activitiesList);

    }
}
