package com.unir.usuarios.infraestructure.persistence;

import com.unir.usuarios.domain.repository.CredencialJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CredencialRepositoryImpl {

  private final CredencialJpaRepository repository;

  public Boolean existeUsername(String username) {
    return repository.existsByUsername(username);
  }

}
