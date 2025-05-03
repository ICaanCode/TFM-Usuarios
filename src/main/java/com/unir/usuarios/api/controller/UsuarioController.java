package com.unir.usuarios.api.controller;

import com.unir.usuarios.api.dto.usuario.autenticar.AuthRequest;
import com.unir.usuarios.api.dto.usuario.autenticar.AuthResponse;
import com.unir.usuarios.api.dto.usuario.crear.CrearUsuarioRequest;
import com.unir.usuarios.api.dto.usuario.modificar.ModificarPassword;
import com.unir.usuarios.api.dto.usuario.modificar.ModificarUsuarioRequest;
import com.unir.usuarios.api.dto.usuario.UsuarioDTO;
import com.unir.usuarios.api.response.ApiResponse;
import com.unir.usuarios.application.usecases.AuthUseCase;
import com.unir.usuarios.application.usecases.UsuarioUseCase;
import com.unir.usuarios.domain.model.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuarios")
@Validated
public class UsuarioController {

  private final AuthUseCase authUseCase;
  private final UsuarioUseCase usuarioUseCase;

  @GetMapping("/{parametroBusqueda}")
  public ResponseEntity<Map<String, Object>> buscarUsuario(@PathVariable String parametroBusqueda) {
    Usuario usuarioEncontrado = usuarioUseCase.obtenerUsuario(parametroBusqueda);
    UsuarioDTO usuarioFormateado = usuarioUseCase.formatearUsuario(usuarioEncontrado);
    return ApiResponse.success(usuarioFormateado, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<Map<String, Object>> listarUsuarios() {
    List<UsuarioDTO> usuarios = usuarioUseCase.obtenerUsuarios();
    return ApiResponse.success(usuarios, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> crearUsuario(@Valid @RequestBody CrearUsuarioRequest request) {
    Usuario usuario = usuarioUseCase.crearUsuario(request);
    return ApiResponse.success(usuario, HttpStatus.CREATED);
  }

  @PatchMapping("/{parametroBusqueda}")
  public ResponseEntity<Map<String, Object>> modificarUsuario(
      @PathVariable String parametroBusqueda,
      @Valid @RequestBody ModificarUsuarioRequest modificaciones
  ) {
    Usuario usuarioModificado = usuarioUseCase.modificarUsuario(modificaciones, parametroBusqueda);
    return ApiResponse.success(usuarioModificado, HttpStatus.OK);
  }

  @PatchMapping("/passwords/{parametroBusqueda}")
  public ResponseEntity<Map<String, Object>> cambiarUsuarioPassword(
      @PathVariable String parametroBusqueda,
      @Valid @RequestBody ModificarPassword modificarPassword
  ) {
    Usuario usuarioModificado = usuarioUseCase.modificarUsuarioPassword(parametroBusqueda, modificarPassword);
    String mensajeModificacionPassword = String.format("Password para el usuario con ID '%s' modificado con éxito.", usuarioModificado.getIdUsuario());
    return ApiResponse.success(mensajeModificacionPassword, HttpStatus.OK);
  }

  @DeleteMapping("/{parametroBusqueda}")
  public ResponseEntity<Map<String, Object>> eliminarUsuario(@PathVariable String parametroBusqueda) {
    usuarioUseCase.eliminarUsuario(parametroBusqueda);
    String mensajeEliminacion = String.format("Usuario relacionado con el parámetro '%s' eliminado.", parametroBusqueda);
    return ApiResponse.success(mensajeEliminacion, HttpStatus.OK);
  }

  @PostMapping("/sesiones")
  public ResponseEntity<Map<String, Object>> iniciarSesion(@Valid @RequestBody AuthRequest solicitud) {
    AuthResponse authResponse = authUseCase.autenticarUsuario(solicitud);
    return ApiResponse.success(authResponse, HttpStatus.OK);
  }

}
