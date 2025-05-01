package com.unir.usuarios.infraestructure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

  private T data;
  private Boolean success;
  private String timestamp;

}
