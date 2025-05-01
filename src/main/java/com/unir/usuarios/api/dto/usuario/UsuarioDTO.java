package com.unir.usuarios.api.dto.usuario;

import com.unir.usuarios.application.dto.ServicioUsuarioDTO;
import com.unir.usuarios.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
  private List<ServicioUsuarioDTO> servicios;
  private LocalDateTime fechaCreacion;
  private LocalDateTime fechaModificacion;

  public UsuarioDTO(Usuario usuario, List<ServicioUsuarioDTO> servicios) {
    this.nombres = usuario.getNombres();
    this.apellidos = usuario.getApellidos();
    this.email = usuario.getEmail();
    this.identificacion = usuario.getIdentificacion();
    this.rol = usuario.getRol().getCodigo();
    this.username = usuario.getCredencial().getUsername();
    this.servicios = servicios;
    this.fechaCreacion = usuario.getFechaCreacion();
    this.fechaModificacion = usuario.getFechaModificacion();
  }

}
