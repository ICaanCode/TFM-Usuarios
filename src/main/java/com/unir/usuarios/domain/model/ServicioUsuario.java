package com.unir.usuarios.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unir.usuarios.application.dto.Servicio;
import com.unir.usuarios.domain.model.index.ServicioId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.*;

@Entity
@Table(schema = "usuario", name = "servicio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioUsuario {

  @EmbeddedId
  private ServicioId id;

  @Column(name = "activo", nullable = false)
  private Boolean activo;

  @Column(name = "fecha_creacion", nullable = false)
  private LocalDateTime fechaCreacion;

  @Column(name = "fecha_modificacion")
  private LocalDateTime fechaModificacion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario")
  @MapsId("usuarioId")
  @ToString.Exclude
  private Usuario usuario;

  @PrePersist
  public void onCreate() {
    this.fechaCreacion = LocalDateTime.now();
  }

  @PreUpdate
  public void onUpdate() {
    this.fechaModificacion = LocalDateTime.now();
  }

  public ServicioUsuario(Integer servicioId, Boolean activo, Usuario usuario) {
    this.id = new ServicioId(usuario.getIdUsuario(), servicioId);
    this.activo = activo;
    this.usuario = usuario;
  }

}
