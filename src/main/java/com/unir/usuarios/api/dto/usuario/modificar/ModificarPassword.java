package com.unir.usuarios.api.dto.usuario.modificar;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ModificarPassword {

  @NotBlank(message = "Debe suministrar el password que quiere cambiar.")
  public String antiguoPassword;

  @NotBlank(message = "Debe suministrar un nuevo password.")
  public String nuevoPassword;

}
