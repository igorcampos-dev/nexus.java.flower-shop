package com.flowershop.back.domain.flower;

import com.flowershop.back.configuration.annotations.IsValid;
import org.springframework.web.multipart.MultipartFile;

public record NewFlowerDTO(@IsValid String fileName,
                           @IsValid MultipartFile file) {}
