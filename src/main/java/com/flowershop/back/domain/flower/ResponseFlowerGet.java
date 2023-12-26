package com.flowershop.back.domain.flower;

import com.flowershop.back.configuration.annotations.IsValid;
import com.flowershop.back.configuration.annotations.ValidBase64;

public record ResponseFlowerGet(@IsValid String id,
                                @IsValid String fileName,
                                @IsValid
                                @ValidBase64
                                String file) {
}
