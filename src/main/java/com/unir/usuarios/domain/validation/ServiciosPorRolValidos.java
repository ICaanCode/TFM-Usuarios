package com.unir.usuarios.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ServiciosPorRolValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiciosPorRolValidos {
  String message() default "La combinación de rol y servicios no es válida.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
