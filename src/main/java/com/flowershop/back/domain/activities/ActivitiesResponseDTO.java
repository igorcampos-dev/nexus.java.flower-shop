package com.flowershop.back.domain.activities;

import com.flowershop.back.configuration.annotations.isValid;
import java.time.LocalDateTime;

public record ActivitiesResponseDTO(String user, String remetente, LocalDateTime localDateTime) {

    public ActivitiesResponseDTO( @isValid Activities activities){
        this(activities.getUser(), activities.getRemittent(), activities.getLocalDateTime());
    }


}
