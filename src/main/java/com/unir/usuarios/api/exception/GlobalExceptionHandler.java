package com.unir.usuarios.api.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.unir.usuarios.api.response.ApiResponse;
import com.unir.usuarios.application.validation.ErrorMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException exception) {
    Map<String, Object> errors = new HashMap<>();

    exception.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return ApiResponse.error(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(JpaObjectRetrievalFailureException.class)
  public ResponseEntity<Map<String, Object>> handleJpaObjectRetrievalFailureException(JpaObjectRetrievalFailureException ex) {
    if (ex.getCause() instanceof EntityNotFoundException) {
      return ApiResponse.error("Entidad no encontrada: " + ex.getCause().getMessage(), HttpStatus.NOT_FOUND);
    }
    return ApiResponse.error("Error al recuperar la entidad: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException exception) {
    return ApiResponse.error(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getConstraintViolations().forEach(violation -> {
      String propertyPath = violation.getPropertyPath().toString();
      String message = violation.getMessage();
      errors.put(propertyPath, message);
    });
    return ApiResponse.error(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    Map<String, Object> errors = new HashMap<>();
    Throwable cause = ex.getCause();

    if (cause instanceof InvalidFormatException) {
      InvalidFormatException invalidFormatException = (InvalidFormatException) cause;
      String fieldName = invalidFormatException.getPath().get(0).getFieldName();
      Class<?> targetType = invalidFormatException.getTargetType();

      String message = ErrorMapper.getErrorMessage(targetType, fieldName);
      errors.put(fieldName, message);
    } else {
      errors.put("error", "El formato del JSON enviado no es válido. Verifique los datos proporcionados.");
    }

    return ApiResponse.error(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGeneralException(Exception exception) {
    return ApiResponse.error(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
