package com.flowershop.back.configuration.annotations.impl;

import com.flowershop.back.configuration.annotations.IsValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsValidImpl implements ConstraintValidator<IsValid, String> {

    @Override
    public void initialize(IsValid constraintAnnotation) {ConstraintValidator.super.initialize(constraintAnnotation);}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.trim().isEmpty();
    }
}
