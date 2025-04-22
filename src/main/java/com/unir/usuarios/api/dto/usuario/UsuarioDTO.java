package com.unir.usuarios.api.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioDTO {
  private String nombres;
  private String apellidos;
  private String email;
  private String identificacion;
  private Integer rol;
  private String username;
}
