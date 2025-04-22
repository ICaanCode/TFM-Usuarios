package com.unir.usuarios.application.validation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class ErrorMapper {

  private static final Map<Class<?>, String> errorMessages = Map.of(
      LocalDate.class, "La fecha en el campo '%s' debe tener el formato 'yyyy-MM-dd'.",
      Integer.class, "El campo '%s' debe ser un número entero.",
      Boolean.class, "El campo '%s' debe ser true o false.",
      LocalTime.class, "La hora en el campo '%s' debe tener el formato 'HH:mm:ss'.",
      LocalDateTime.class, "La fecha y hora en el campo '%s' debe tener el formato 'yyyy-MM-dd'T'HH:mm:ss'."
  );

  public static String getErrorMessage(Class<?> type, String fieldName) {
    if (type.isEnum()) {
      return String.format("El campo '%s' tiene un valor no permitido.", fieldName);
    }
    if (errorMessages.containsKey(type)) {
      return String.format(errorMessages.get(type), fieldName);
    }
    return String.format("El valor proporcionado en '%s' no tiene el formato correcto.", fieldName);
  }

}
