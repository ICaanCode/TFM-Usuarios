package com.unir.usuarios.infraestructure.feign;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomFeignErrorDecoder implements ErrorDecoder {

  private final ErrorDecoder defaultErrorDecoder = new Default();

  @Override
  public Exception decode(String methodKey, Response response) {
    String serviceName = methodKey.split("#")[0];

    String errorMessage = "";

    try {
      String body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
      ObjectMapper mapper = new ObjectMapper();
      JsonNode json = mapper.readTree(body);
      errorMessage = json.path("error").asText("Error desconocido");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    if (response.status() == HttpStatus.NOT_FOUND.value()) {
      return new EntityNotFoundException("Recurso no encontrado en " + serviceName + ": " + errorMessage);
    }
    if (response.status() >= 400 && response.status() < 500) {
      return new IllegalArgumentException("Error en la solicitud al servicio " + serviceName + ": " + errorMessage);
    }
    if (response.status() >= 500) {
      return new RuntimeException("Error interno en el servicio " + serviceName + ": " + errorMessage);
    }

    return defaultErrorDecoder.decode(methodKey, response);
  }

}
