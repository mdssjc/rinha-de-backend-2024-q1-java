package dev.marcelomds.rinhadebackend2024q1.data.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TypeValidator implements ConstraintValidator<Type, String> {

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        return "c".equals(input) || "d".equals(input);
    }
}
