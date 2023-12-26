package com.flowershop.back.domain.activities;

import com.flowershop.back.configuration.annotations.IsValid;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record ActivitiesResponseDTO(

        @Schema(description = "email do usuário",
                example = "exemploUsuario@example.com")
        @IsValid
        String user,

        @Schema(description = "quem o usuário enviou o email",
                example = "exemploRemetente@example.com")
        @IsValid
        List<String> remetentes,

        @Schema(description = "horário de envio das mensagens",
                example = "2023-12-19T12:56:02.823Z")
        @IsValid
        List<LocalDateTime> localDateTimes) {

    public ActivitiesResponseDTO(@IsValid List<Activities> activitiesList) {
        this(activitiesList.isEmpty() ? null : activitiesList.get(0).getUser(),
                activitiesList.stream().map(Activities::getRemittent).toList(),
                activitiesList.stream().map(Activities::getDatetime).toList());
    }
}
