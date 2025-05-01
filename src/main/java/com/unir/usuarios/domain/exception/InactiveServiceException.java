package com.unir.usuarios.domain.exception;

public class InactiveServiceException extends RuntimeException {
  public InactiveServiceException(String message) {
    super(message);
  }
}
