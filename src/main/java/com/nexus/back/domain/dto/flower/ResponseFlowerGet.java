package com.nexus.back.domain.dto.flower;

import com.nexus.validations.NonNullOrBlank;

public record ResponseFlowerGet(@NonNullOrBlank String id,
                                @NonNullOrBlank String fileName,
                                @NonNullOrBlank
                                String file) {
}
