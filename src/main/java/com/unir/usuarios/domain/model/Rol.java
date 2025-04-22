package com.unir.usuarios.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(schema = "catalogo", name = "rol")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Rol {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_rol", updatable = false, nullable = false)
  private Integer idRol;

  @Column(name = "nombre", nullable = false, unique = true, length = 50)
  private String nombre;

  @Column(name = "codigo", nullable = false, unique = true)
  private Integer codigo;

  @Column(name = "activo", nullable = false)
  private Boolean activo;

  @Column(name = "fecha_creacion", nullable = false, updatable = false)
  private LocalDateTime fechaCreacion;

  @Column(name = "fecha_modificacion")
  private LocalDateTime fechaModificacion;

  @PrePersist
  public void onCreate() {
    this.fechaCreacion = LocalDateTime.now();
  }

  @PreUpdate
  public void onUpdate() {
    this.fechaModificacion = LocalDateTime.now();
  }

}
