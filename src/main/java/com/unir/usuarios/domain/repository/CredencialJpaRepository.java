package com.unir.usuarios.domain.repository;

import com.unir.usuarios.domain.model.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CredencialJpaRepository extends JpaRepository<Credencial, UUID> {

  public Boolean existsByUsername(String username);

}
