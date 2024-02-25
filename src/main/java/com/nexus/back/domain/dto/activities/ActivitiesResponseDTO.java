package com.nexus.back.domain.dto.activities;

import com.nexus.back.domain.entity.Activities;
import com.nexus.validations.NonNullOrBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public record ActivitiesResponseDTO(

        @Schema(description = "email do usuário",
                example = "exemploUsuario@example.com")
        @NonNullOrBlank
        String user,

        @Schema(description = "quem o usuário enviou o email",
                example = "exemploRemetente@example.com")
        @NonNullOrBlank
        List<String> remetentes,

        @Schema(description = "horário de envio das mensagens",
                example = "2023-12-19T12:56:02.823Z")
        @NonNullOrBlank
        List<LocalDateTime> localDateTimes) {

    public ActivitiesResponseDTO(@NonNullOrBlank List<Activities> activitiesList) {
        this(activitiesList.isEmpty() ? null : activitiesList.get(0).getUser(),
                activitiesList.stream().map(Activities::getRemittent).toList(),
                activitiesList.stream().map(Activities::getDatetime).toList());
    }
}
