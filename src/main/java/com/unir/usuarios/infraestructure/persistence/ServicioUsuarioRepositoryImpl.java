package com.unir.usuarios.infraestructure.persistence;

import com.unir.usuarios.domain.model.ServicioUsuario;
import com.unir.usuarios.domain.model.index.ServicioId;
import com.unir.usuarios.domain.repository.ServicioUsuarioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ServicioUsuarioRepositoryImpl {

  private final ServicioUsuarioJpaRepository repository;

  public ServicioUsuario obtenerServicioPorId(UUID usuarioId, Integer servicioId) {
    return repository.findById(new ServicioId(usuarioId, servicioId)).orElse(null);
  }

  public List<ServicioUsuario> obtenerServiciosPorUsuarioId(UUID usuarioId) {
    return repository.findByIdUsuarioId(usuarioId);
  }

  public List<ServicioUsuario> guardarTodosServicios(List<ServicioUsuario> servicios) {
    return repository.saveAll(servicios);
  }

}
