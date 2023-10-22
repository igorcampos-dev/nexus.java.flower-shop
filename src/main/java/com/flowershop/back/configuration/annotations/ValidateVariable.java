package com.flowershop.back.configuration.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateVariable implements ConstraintValidator<isValid, String> {

    @Override
    public void initialize(isValid constraintAnnotation) {ConstraintValidator.super.initialize(constraintAnnotation);}


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.trim().isEmpty();
    }
}
