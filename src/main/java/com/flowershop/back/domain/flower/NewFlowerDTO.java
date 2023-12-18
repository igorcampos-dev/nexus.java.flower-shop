package com.flowershop.back.domain.flower;

import com.flowershop.back.configuration.annotations.isValid;
import org.springframework.web.multipart.MultipartFile;

public record NewFlowerDTO(@isValid String fileName,
                           @isValid MultipartFile file) {}
