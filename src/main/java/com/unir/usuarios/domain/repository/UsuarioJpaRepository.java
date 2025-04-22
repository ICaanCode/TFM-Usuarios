package com.unir.usuarios.domain.repository;

import com.unir.usuarios.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioJpaRepository extends JpaRepository<Usuario, UUID> {

  @Query("SELECT u FROM Usuario u WHERE u.credencial.username = :username")
  public Optional<Usuario> findByCredencialUsername(@Param("username") String username);

  public Optional<Usuario> findByEmail(String email);

  public Optional<Usuario> findByIdentificacion(String identificacion);

  public Boolean existsByEmail(String email);

  public Boolean existsByIdentificacion(String identificacion);

}
