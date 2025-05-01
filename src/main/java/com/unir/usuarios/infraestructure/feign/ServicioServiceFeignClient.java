package com.unir.usuarios.infraestructure.feign;

import com.unir.usuarios.infraestructure.dto.ApiResponse;
import com.unir.usuarios.infraestructure.dto.servicio.ServicioDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
    name = "servicios",
    configuration = FeignConfiguration.class
)
public interface ServicioServiceFeignClient {

  @GetMapping("/api/servicios")
  ApiResponse<List<ServicioDataDTO>> obtenerServicios();

}
