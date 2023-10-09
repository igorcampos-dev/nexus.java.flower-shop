package com.flowershop.back.domain.flower;

import com.flowershop.back.configuration.annotations.isValid;

public record FlowerDTO(@isValid String image, @isValid String name) {
}
