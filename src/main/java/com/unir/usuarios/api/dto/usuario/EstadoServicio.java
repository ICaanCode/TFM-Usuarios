package com.unir.usuarios.api.dto.usuario;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstadoServicio {

  @NotNull(message = "Debe proporcionar el código del servicio que está modificando.")
  @Min(value = 30000, message = "El valor del código del servicio debe ser mayor a 30000.")
  @Max(value = 31000, message = "El valor del código del servicio debe ser menor a 31000.")
  private Integer codigoServicio;

  @NotNull(message = "Debe indicar si el servicio está activo o no por medio de un booleano.")
  private Boolean activo;

}
