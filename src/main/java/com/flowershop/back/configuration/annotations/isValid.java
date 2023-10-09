package com.flowershop.back.configuration.annotations;

import com.flowershop.back.configuration.ValidateVariable;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidateVariable.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface isValid {
    String message() default "A string não é valida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
