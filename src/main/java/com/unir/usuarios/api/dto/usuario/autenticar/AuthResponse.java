package com.unir.usuarios.api.dto.usuario.autenticar;

import com.unir.usuarios.domain.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AuthResponse {

  private String token;
  private UUID usuarioId;
  private String nombres;
  private String apellidos;
  private Integer codigoRol;
  private List<Integer> servicios;

}
