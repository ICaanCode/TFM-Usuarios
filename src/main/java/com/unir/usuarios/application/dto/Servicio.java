package com.unir.usuarios.application.dto;

import com.unir.usuarios.infraestructure.dto.servicio.ServicioDataDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Servicio {

  private Integer servicioId;
  private Integer codigo;
  private String nombre;
  private Boolean activo;

  public Servicio(ServicioDataDTO servicio) {
    this.servicioId = servicio.getIdServicio();
    this.codigo = servicio.getCodigo();
    this.nombre = servicio.getNombre();
    this.activo = servicio.getActivo();
  }

  public Boolean buscarPorCodigoOServicioId(Integer valor) {
    if (valor == null) return false;
    return this.servicioId.equals(valor) || this.codigo.equals(valor);
  }

}
