package com.nexus.back.domain.dto.flower;

import com.nexus.validations.NonNullOrBlank;

public record FlowerGetDatabase(@NonNullOrBlank String filename,
                                @NonNullOrBlank String file) {
}
