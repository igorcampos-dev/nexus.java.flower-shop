package com.nexus.back.domain.dto.flower;

import com.nexus.validations.NonNullOrBlank;
import org.springframework.web.multipart.MultipartFile;

public record NewFlowerDTO(@NonNullOrBlank String fileName,
                           @NonNullOrBlank MultipartFile file) {}
