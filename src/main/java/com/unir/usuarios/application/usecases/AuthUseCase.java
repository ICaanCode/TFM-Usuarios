package com.unir.usuarios.application.usecases;

import com.unir.usuarios.api.dto.usuario.autenticar.AuthRequest;
import com.unir.usuarios.api.dto.usuario.autenticar.AuthResponse;
import com.unir.usuarios.application.util.ServicioServicio;
import com.unir.usuarios.domain.model.Usuario;
import com.unir.usuarios.infraestructure.dto.seguridad.AuthRequestDTO;
import com.unir.usuarios.infraestructure.feign.SeguridadServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthUseCase {

  private final UsuarioUseCase usuarioUseCase;

  private final SeguridadServiceFeignClient seguridadCliente;

  private final ServicioServicio servicioServicio;

  public AuthResponse autenticarUsuario(AuthRequest authRequest) {
    try {
      Usuario usuarioEncontrado = usuarioUseCase.obtenerUsuario(authRequest.getParametroBusqueda());
      String token = seguridadCliente.obtenerToken(new AuthRequestDTO(usuarioEncontrado.getCredencial().getUsername(), authRequest.getPassword())).getData().getToken();
      List<Integer> codigosServicios = usuarioEncontrado.getServicios().stream().map(s -> {
        try {
          if (Boolean.FALSE.equals(s.getActivo())) return null;
          return servicioServicio.obtenerServicio(s.getId().getServicioId(), false).getCodigo();
        } catch (Exception e) {
          return null;
        }
      }).filter(Objects::nonNull).collect(Collectors.toList());
      return new AuthResponse(
          token, usuarioEncontrado.getIdUsuario(), usuarioEncontrado.getNombres(), usuarioEncontrado.getApellidos(), usuarioEncontrado.getRol().getCodigo(), codigosServicios
      );
    } catch (Exception e) {
      System.err.println("\nExcepcion: " + e.getMessage() + "\n");
      throw new IllegalArgumentException("El usuario o el password no corresponden.");
    }
  }

}
