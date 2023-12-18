package com.flowershop.back.domain.activities;

import com.flowershop.back.configuration.annotations.isValid;
import java.time.LocalDateTime;
import java.util.List;

public record ActivitiesResponseDTO(@isValid String user,
                                    @isValid List<String> remetentes,
                                    @isValid List<LocalDateTime> localDateTimes) {

    public ActivitiesResponseDTO(@isValid List<Activities> activitiesList) {
        this(activitiesList.isEmpty() ? null : activitiesList.get(0).getUser(),
                activitiesList.stream().map(Activities::getRemittent).toList(),
                activitiesList.stream().map(Activities::getDatetime).toList());
    }
}
