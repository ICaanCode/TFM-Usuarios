package com.unir.usuarios.infraestructure.persistence;

import com.unir.usuarios.domain.model.Rol;
import com.unir.usuarios.domain.repository.RolJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RolRepositoryImpl {

  public final RolJpaRepository repository;

  public Rol obtenerRolPorCodigo(Integer codigo) {
    return repository
        .findByCodigo(codigo)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Rol con código '%s' no encontrado", codigo)));
  }

}
