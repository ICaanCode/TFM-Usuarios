package com.unir.usuarios.application.usecases;

import com.unir.usuarios.api.dto.usuario.crear.CrearUsuarioRequest;
import com.unir.usuarios.api.dto.usuario.modificar.ModificarPassword;
import com.unir.usuarios.api.dto.usuario.modificar.ModificarUsuarioRequest;
import com.unir.usuarios.api.dto.usuario.UsuarioDTO;
import com.unir.usuarios.application.dto.ServicioUsuarioDTO;
import com.unir.usuarios.domain.model.Credencial;
import com.unir.usuarios.domain.model.Rol;
import com.unir.usuarios.domain.model.ServicioUsuario;
import com.unir.usuarios.domain.model.Usuario;
import com.unir.usuarios.infraestructure.persistence.CredencialRepositoryImpl;
import com.unir.usuarios.infraestructure.persistence.RolRepositoryImpl;
import com.unir.usuarios.infraestructure.persistence.UsuarioRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioUseCase {

  private final ServicioUseCase servicioUseCase;

  private final CredencialRepositoryImpl credencialRepository;
  private final RolRepositoryImpl rolRepository;
  private final UsuarioRepositoryImpl usuarioRepository;

  private final PasswordEncoder passwordEncoder;

  public List<UsuarioDTO> obtenerUsuarios() {

    return usuarioRepository.obtenerUsuarios().stream().map(this::formatearUsuario).collect(Collectors.toList());

  }

  public Usuario obtenerUsuario(String parametro) {
    return usuarioRepository.obtenerUsuario(parametro);
  }

  @Transactional
  public Usuario crearUsuario(CrearUsuarioRequest usuario) {

    Rol rol = rolRepository.obtenerRolPorCodigo(usuario.getCodigoRol());

    if (verificarIdentificacion(usuario.getIdentificacion())) {
      throw new IllegalArgumentException(String.format("La identificación '%s' ya está registrada.", usuario.getIdentificacion()));
    }
    if (verificarEmail(usuario.getEmail())) {
      throw new IllegalArgumentException(String.format("El correo electrónico '%s' ya está registrado.", usuario.getEmail()));
    }
    if (verificarUsername(usuario.getUsername())) {
      throw new IllegalArgumentException(String.format("El nombre de usuario '%s' ya está registrado.", usuario.getUsername()));
    }

    Usuario nuevoUsuario = Usuario.builder().nombres(usuario.getNombres()).apellidos(usuario.getApellidos()).email(usuario.getEmail()).identificacion(usuario.getIdentificacion()).rol(rol).build();

    Credencial nuevaCredencial = Credencial.builder().usuario(nuevoUsuario).username(usuario.getUsername()).passwordHash(passwordEncoder.encode(usuario.getPassword())).activo(true).build();

    nuevoUsuario.setCredencial(nuevaCredencial);

    if (usuario.getCodigoRol().equals(10001)) {
      List<ServicioUsuario> servicios = servicioUseCase.actualizarServiciosUsuario(usuario.getServicios(), nuevoUsuario);
      nuevoUsuario.setServicios(servicios);
    }

    return usuarioRepository.crearUsuario(nuevoUsuario);

  }

  @Transactional
  public Usuario modificarUsuario(ModificarUsuarioRequest modificaciones, String parametroBusqueda) {

    Usuario usuario = usuarioRepository.obtenerUsuario(parametroBusqueda);

    if (modificaciones.getCodigoRol() != null) {
      Rol rol = rolRepository.obtenerRolPorCodigo(modificaciones.getCodigoRol());
      usuario.setRol(rol);
      if (!usuario.getRol().equals(10001)) {
        usuario.setServicios(servicioUseCase.desactivarServiciosUsuario(usuario));
      }
    }

    if (modificaciones.getNombres() != null) {
      usuario.setNombres(modificaciones.getNombres());
    }

    if (modificaciones.getApellidos() != null) {
      usuario.setApellidos(modificaciones.getApellidos());
    }

    if (modificaciones.getEmail() != null) {
      verificarEmail(modificaciones.getEmail());
      usuario.setEmail(modificaciones.getEmail());
    }

    if (modificaciones.getIdentificacion() != null) {
      verificarIdentificacion(modificaciones.getIdentificacion());
      usuario.setIdentificacion(modificaciones.getIdentificacion());
    }

    if (modificaciones.getUsername() != null) {
      verificarUsername(modificaciones.getUsername());
      usuario.getCredencial().setUsername(modificaciones.getUsername());
    }

    if (modificaciones.getServicios() != null && !modificaciones.getServicios().isEmpty()) {
      List<ServicioUsuario> servicios = servicioUseCase.actualizarServiciosUsuario(modificaciones.getServicios(), usuario);
      usuario.setServicios(servicios);
    }

    return usuarioRepository.guardarUsuario(usuario);

  }

  public Usuario modificarUsuarioPassword(String parametroBusqueda, ModificarPassword credencial) {

    Usuario usuarioModificar = usuarioRepository.obtenerUsuario(parametroBusqueda);

    String antiguoPasswordHash = usuarioModificar.getCredencial().getPasswordHash();

    if (!passwordEncoder.matches(credencial.getAntiguoPassword(), antiguoPasswordHash))
      throw new IllegalArgumentException("Usuario o password no válidos.");

    String nuevoPasswordHash = passwordEncoder.encode(credencial.getNuevoPassword());

    usuarioModificar.getCredencial().setPasswordHash(nuevoPasswordHash);

    return usuarioRepository.guardarUsuario(usuarioModificar);

  }

  public Usuario eliminarUsuario(String parametroBusqueda) {
    return usuarioRepository.eliminarUsuario(parametroBusqueda);
  }

  private Boolean verificarIdentificacion(String identificacion) {
    return usuarioRepository.existeIdentificacion(identificacion);
  }

  private Boolean verificarEmail(String email) {
    return usuarioRepository.existeEmail(email);
  }

  private Boolean verificarUsername(String nuevoUsername) {
    return credencialRepository.existeUsername(nuevoUsername);
  }

  public UsuarioDTO formatearUsuario(Usuario usuario) {
    List<ServicioUsuarioDTO> listaServcios = servicioUseCase.listarServiciosPorUsuarioId(usuario.getIdUsuario());
    return new UsuarioDTO(usuario, listaServcios);
  }

}
