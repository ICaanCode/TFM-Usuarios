package com.unir.usuarios.domain.repository;

import com.unir.usuarios.domain.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolJpaRepository extends JpaRepository<Rol, Integer> {

  public Optional<Rol> findByCodigo(Integer codigo);

}
