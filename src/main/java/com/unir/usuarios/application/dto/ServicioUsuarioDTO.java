package com.unir.usuarios.application.dto;

import com.unir.usuarios.domain.model.ServicioUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioUsuarioDTO {

  private Integer codigoServicio;
  private Boolean activo;
  private LocalDateTime fechaCreacion;
  private LocalDateTime fechaModificacion;

  public ServicioUsuarioDTO(Integer codigoServicio, ServicioUsuario servicioUsuario) {
    this.codigoServicio = codigoServicio;
    this.activo = servicioUsuario.getActivo();
    this.fechaCreacion = servicioUsuario.getFechaCreacion();
    this.fechaModificacion = servicioUsuario.getFechaModificacion();
  }

}
