package com.unir.usuarios.infraestructure.feign;

import com.unir.usuarios.infraestructure.dto.ApiResponse;
import com.unir.usuarios.infraestructure.dto.seguridad.AuthRequestDTO;
import com.unir.usuarios.infraestructure.dto.seguridad.AuthResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "seguridad",
    configuration = FeignConfiguration.class
)
public interface SeguridadServiceFeignClient {

  @PostMapping("/api/auth/login")
  ApiResponse<AuthResponseDTO> obtenerToken(@RequestBody AuthRequestDTO authRequestDTO);

}
