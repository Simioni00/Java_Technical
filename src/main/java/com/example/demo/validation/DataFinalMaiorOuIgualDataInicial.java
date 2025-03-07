package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DataFinalMaiorOuIgualDataInicialValidator.class)
public @interface DataFinalMaiorOuIgualDataInicial {
    String message() default "A data final deve ser maior ou igual à data inicial";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}