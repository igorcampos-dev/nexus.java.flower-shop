package com.flowershop.back.domain.flower;

import com.flowershop.back.configuration.annotations.isValid;

public record FlowerUpdateDTO(@isValid String name,@isValid String image, @isValid String newName) {
}
