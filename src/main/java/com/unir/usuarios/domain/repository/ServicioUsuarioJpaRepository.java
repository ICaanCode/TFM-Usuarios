package com.unir.usuarios.domain.repository;

import com.unir.usuarios.domain.model.ServicioUsuario;
import com.unir.usuarios.domain.model.index.ServicioId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServicioUsuarioJpaRepository extends JpaRepository<ServicioUsuario, ServicioId> {

  public Optional<ServicioUsuario> findById(ServicioId servicioId);
  public List<ServicioUsuario> findByIdUsuarioId(UUID usuarioId);

}
