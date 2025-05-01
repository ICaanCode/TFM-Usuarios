package com.unir.usuarios.application.util;

import com.unir.usuarios.application.dto.Servicio;
import com.unir.usuarios.domain.exception.InactiveServiceException;
import com.unir.usuarios.infraestructure.dto.servicio.ServicioDataDTO;
import com.unir.usuarios.infraestructure.feign.ServicioServiceFeignClient;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicioServicio {

  private final ServicioServiceFeignClient servicioService;

  private List<Servicio> servicios = new ArrayList<>();

  @PostConstruct
  public void init() {
    List<ServicioDataDTO> serviciosData = servicioService.obtenerServicios().getData();
    this.servicios = serviciosData.stream().map(Servicio::new).collect(Collectors.toList());
  }

  public Servicio obtenerServicio(Integer parametroBusqueda, Boolean validarActivo) {
    Servicio servicio = servicios.stream().filter(s -> s.buscarPorCodigoOServicioId(parametroBusqueda)).findFirst().orElseThrow(() -> new EntityNotFoundException(String.format("El servicio con código '%d' no existe.", parametroBusqueda)));
    if (Boolean.FALSE.equals(servicio.getActivo()) && validarActivo) throw new InactiveServiceException(String.format("El servicio '%s' está inactivo.", servicio.getNombre()));
    return servicio;
  }

}