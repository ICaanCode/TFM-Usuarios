package com.unir.usuarios.infraestructure.dto.servicio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioDataDTO {

  private Integer idServicio;
  private Integer codigo;
  private String nombre;
  private String descripcion;
  private Boolean activo;
  private LocalDateTime fechaCreacion;
  private LocalDateTime fechaModificacion;

}
