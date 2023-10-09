package com.flowershop.back.domain.activities;

import com.flowershop.back.configuration.annotations.isValid;

import java.time.LocalDateTime;

public record ActivitiesDTO(@isValid String user, @isValid String remetente, @isValid LocalDateTime localDateTime) {
}
