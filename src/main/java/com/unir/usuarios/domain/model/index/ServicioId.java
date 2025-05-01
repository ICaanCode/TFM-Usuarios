package com.unir.usuarios.domain.model.index;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioId implements Serializable {

  @Column(name = "usuario_id")
  private UUID usuarioId;

  @Column(name = "servicio_id")
  private Integer servicioId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ServicioId)) return false;
    ServicioId that = (ServicioId) o;
    return Objects.equals(usuarioId, that.usuarioId) && Objects.equals(servicioId, that.servicioId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(usuarioId, servicioId);
  }

}
