package dev.marcelomds.rinhadebackend2024q1.data.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Type {

    String message() default "O tipo deve ser 'c' ou 'd'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
