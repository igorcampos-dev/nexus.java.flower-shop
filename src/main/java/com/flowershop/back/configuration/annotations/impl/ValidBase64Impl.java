package com.flowershop.back.configuration.annotations.impl;

import com.flowershop.back.configuration.annotations.ValidBase64;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ValidBase64Impl implements ConstraintValidator<ValidBase64, String> {

    @Override
    public void initialize(ValidBase64 constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(value);

            try (InputStream inputStream = new ByteArrayInputStream(decodedBytes)) {
                BufferedImage image = javax.imageio.ImageIO.read(inputStream);
                if (image == null) {
                    throw new IOException("Falha na validação: Não é uma imagem válida.");
                }
                return true;
            }
        } catch (IOException | IllegalArgumentException e) {
            return false;
        }
    }
}
