package com.flowershop.back.domain.flower;

import com.flowershop.back.configuration.annotations.isValid;

public record FlowerResponseDTO(@isValid String name, @isValid String image) {
    public FlowerResponseDTO(@isValid Flowers flowers){
        this(flowers.getName(), flowers.getImage());
    }
}
