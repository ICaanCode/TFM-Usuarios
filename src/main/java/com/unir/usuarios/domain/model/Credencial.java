package com.unir.usuarios.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "usuario", name = "credencial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Credencial {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_credencial", columnDefinition = "UUID")
  private UUID idCredencial;

  @OneToOne
  @JsonIgnore
  @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario", nullable = false)
  private Usuario usuario;

  @Column(name = "username", nullable = false, unique = true, length = 50)
  private String username;

  @Column(name = "password_hash", nullable = false)
  private String passwordHash;

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
