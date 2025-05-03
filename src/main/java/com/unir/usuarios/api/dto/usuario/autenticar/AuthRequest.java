package com.unir.usuarios.api.dto.usuario.autenticar;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

  @NotBlank(message = "Debe proporcionar el email, identificación o nombre de usuario para iniciar sesión.")
  private String parametroBusqueda;

  @NotBlank(message = "Debe proporcionar la contraseña del usuario para iniciar sesión.")
  private String password;

}
