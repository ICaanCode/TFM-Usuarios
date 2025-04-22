package com.unir.usuarios.api.dto.usuario;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CrearUsuarioRequest {

  @NotBlank(message = "Debe proporcionar el nombre (o nombres) del usuario.")
  private String nombres;

  @NotBlank(message = "Debe proporcionar el apellido (o apellidos) del usuario.")
  private String apellidos;

  @NotBlank(message = "Es indispensable suministrar una cuenta de correo electrónico.")
  @Email(message = "El correo electrónico proporcionado es inválido.")
  private String email;

  @NotBlank(message = "Debe suministrar un número de identificación para el usuario.")
  @Pattern(
      regexp = "^\\d{6,10}$",
      message = "La identificación debe tener entre 6 y 10 dígitos."
  )
  private String identificacion;

  @NotNull(message = "Para crear un usuario debe asignarle un rol.")
  @Min(value = 10001, message = "El valor de los roles es un número entero a partir de 10001.")
  @Max(value = 11000, message = "El valor de los roles es un número entero hasta 11000.")
  private Integer codigoRol;

  @NotBlank(message = "Debe proporcionar un nombre de usuario.")
  private String username;

  @NotBlank(message = "Debe proporcionar una contraseña.")
  private String password;

}
