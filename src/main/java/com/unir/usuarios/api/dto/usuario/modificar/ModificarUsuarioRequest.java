package com.unir.usuarios.api.dto.usuario.modificar;

import com.unir.usuarios.api.dto.usuario.EstadoServicio;
import com.unir.usuarios.domain.validation.ServiciosPorRolValidos;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
@ServiciosPorRolValidos
public class ModificarUsuarioRequest {

  private String nombres;

  private String apellidos;

  @Email(message = "El correo electrónico proporcionado es inválido.")
  private String email;

  @Pattern(
      regexp = "^\\d{6,10}$",
      message = "La identificación debe tener entre 6 y 10 dígitos."
  )
  private String identificacion;

  @Min(value = 10001, message = "El valor de los roles es un número entero a partir de 10001.")
  @Max(value = 11000, message = "El valor de los roles es un número entero hasta 11000.")
  private Integer codigoRol;

  private String username;

  @Valid
  private List<EstadoServicio> servicios;

}
