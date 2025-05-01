package com.unir.usuarios.application.usecases;

import com.unir.usuarios.api.dto.usuario.EstadoServicio;
import com.unir.usuarios.application.dto.Servicio;
import com.unir.usuarios.application.dto.ServicioUsuarioDTO;
import com.unir.usuarios.application.util.ServicioServicio;
import com.unir.usuarios.domain.exception.InactiveServiceException;
import com.unir.usuarios.domain.model.ServicioUsuario;
import com.unir.usuarios.domain.model.Usuario;
import com.unir.usuarios.infraestructure.persistence.ServicioUsuarioRepositoryImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicioUseCase {

  private final ServicioUsuarioRepositoryImpl servicioRepository;
  private final ServicioServicio servicioServicio;

  public List<ServicioUsuarioDTO> listarServiciosPorUsuarioId(UUID usuarioId) {
    List<ServicioUsuario> serviciosUsuario = servicioRepository.obtenerServiciosPorUsuarioId(usuarioId);
    return serviciosUsuario.stream().map((su) -> {
      try {
        Servicio servicio = servicioServicio.obtenerServicio(su.getId().getServicioId(), true);
        return new ServicioUsuarioDTO(servicio.getCodigo(), su);
      } catch (Exception e) {
        return null;
      }
    }).filter(Objects::nonNull).collect(Collectors.toList());
  }

  public List<ServicioUsuario> actualizarServiciosUsuario(List<EstadoServicio> nuevos, Usuario usuario) {
    servicioServicio.init();

    Map<Integer, EstadoServicio> entrada = nuevos.stream()
        .map(s -> {
          try {
            Integer servicioId = servicioServicio.obtenerServicio(s.getCodigoServicio(), false).getServicioId();
            return Map.entry(servicioId, s);
          } catch (Exception e) {
            return null;
          }
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            (s1, s2) -> s2));

    List<ServicioUsuario> existentes = servicioRepository.obtenerServiciosPorUsuarioId(usuario.getIdUsuario());

    Map<Integer, ServicioUsuario> resultado = new HashMap<>();

    for (ServicioUsuario existente : existentes) {
      EstadoServicio nuevo = entrada.remove(existente.getId().getServicioId());
      if (nuevo != null) {
        existente.setActivo(nuevo.getActivo());
        resultado.put(existente.getId().getServicioId(), existente);
      }
    }

    for (Map.Entry<Integer, EstadoServicio> entry : entrada.entrySet()) {
      Integer servicioId = entry.getKey();
      EstadoServicio nuevo = entry.getValue();
      resultado.put(servicioId, new ServicioUsuario(servicioId, nuevo.getActivo(), usuario));
    }

    if (resultado.isEmpty()) {
      throw new EntityNotFoundException("Ninguno de los servicios especificados ha sido encontrado en el registro.");
    }

    boolean hayActivo = resultado.values().stream().anyMatch(s -> Boolean.TRUE.equals(s.getActivo()));
    if (!hayActivo) {
      throw new InactiveServiceException("Ninguno de los servicios del usuario está activo.");
    }

    return new ArrayList<>(resultado.values());
  }

  @Transactional
  public List<ServicioUsuario> desactivarServiciosUsuario(Usuario usuario) {
    List<ServicioUsuario> servicios = servicioRepository.obtenerServiciosPorUsuarioId(usuario.getIdUsuario());
    for (ServicioUsuario servicio : servicios) {
      servicio.setActivo(false);
    }
    return servicioRepository.guardarTodosServicios(servicios);
  }


}
