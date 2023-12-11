package com.flowershop.back.domain.flower;

import com.flowershop.back.configuration.annotations.isValid;

public record FlowerGetDatabase(@isValid String fileName,@isValid byte[] file) {
}
