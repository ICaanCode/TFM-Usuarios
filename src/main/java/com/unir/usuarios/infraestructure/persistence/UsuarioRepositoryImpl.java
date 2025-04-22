package com.unir.usuarios.infraestructure.persistence;

import com.unir.usuarios.domain.model.Usuario;
import com.unir.usuarios.domain.repository.UsuarioJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryImpl {

  private final UsuarioJpaRepository repository;

  public List<Usuario> obtenerUsuarios() {
    return repository.findAll();
  }

  public Usuario obtenerUsuario(String parametro) {
    UUID uuidValido = parseUUID(parametro);
    if (uuidValido != null) {
      return repository.findById(uuidValido).orElseThrow(() -> new EntityNotFoundException(String.format("Usuario con ID '%s' no encontrado.", uuidValido)));
    }
    if (Boolean.TRUE.equals(emailValido(parametro))) {
      return repository.findByEmail(parametro).orElseThrow(() -> new EntityNotFoundException(String.format("Usuario con email '%s' no encontrado.", parametro)));
    }
    if (Boolean.TRUE.equals(identificacionValida(parametro))) {
      return repository.findByIdentificacion(parametro).orElseThrow(() -> new EntityNotFoundException(String.format("Usuario con documento '%s' no encontrado.", parametro)));
    }
    return repository.findByCredencialUsername(parametro).orElseThrow(() -> new EntityNotFoundException(String.format("Nombre de usuario '%s' no encontrado.", parametro)));
  }

  public Usuario guardarUsuario(Usuario usuario) {
    return repository.save(usuario);
  }

  public Usuario eliminarUsuario(String parametro) {
    Usuario usuario = obtenerUsuario(parametro);
    repository.delete(usuario);
    return usuario;
  }

  public Boolean existeIdentificacion(String identificacion) { return repository.existsByIdentificacion(identificacion); }

  public Boolean existeEmail(String email) { return repository.existsByEmail(email); }

  public Usuario crearUsuario(Usuario usuario) {
    return repository.save(usuario);
  }

  private UUID parseUUID(String parametro) {
    try {
      return UUID.fromString(parametro);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  private Boolean emailValido(String email) {
    String emailRegex = "^[a-zA-Z0-9._+-]+@[a-zA-Z0-9._+-]+\\.[a-zA-Z]{2,}$";
    return Pattern.compile(emailRegex).matcher(email).matches();
  }

  private Boolean identificacionValida(String identificacion) {
    String identificacionRegex = "^\\d{6,10}$";
    return Pattern.compile(identificacionRegex).matcher(identificacion).matches();
  }

}
