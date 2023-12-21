package com.flowershop.back.domain.activities;

import com.flowershop.back.configuration.annotations.isValid;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record ActivitiesResponseDTO(

        @Schema(description = "email do usuário",
                example = "exemploUsuario@example.com")
        @isValid
        String user,

        @Schema(description = "quem o usuário enviou o email",
                example = "exemploRemetente@example.com")
        @isValid
        List<String> remetentes,

        @Schema(description = "horário de envio das mensagens",
                example = "2023-12-19T12:56:02.823Z")
        @isValid
        List<LocalDateTime> localDateTimes) {

    public ActivitiesResponseDTO(@isValid List<Activities> activitiesList) {
        this(activitiesList.isEmpty() ? null : activitiesList.get(0).getUser(),
                activitiesList.stream().map(Activities::getRemittent).toList(),
                activitiesList.stream().map(Activities::getDatetime).toList());
    }
}
