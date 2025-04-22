package com.unir.usuarios.api.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ToString
public class ApiResponse {

  private Map<String, Object> body;

  public ApiResponse() {
    this.body = new HashMap<>();
    this.body.put("timestamp", LocalDateTime.now());
  }

  public static ResponseEntity<Map<String, Object>> success(Object value, HttpStatus status) {
    ApiResponse response = new ApiResponse();
    response.body.put("success", true);
    response.body.put("data", value);
    return new ResponseEntity<>(response.body, status);
  }

  public static ResponseEntity<Map<String, Object>> error(Object error, HttpStatus status) {
    ApiResponse response = new ApiResponse();
    response.body.put("success", false);
    response.body.put("error", error);
    return new ResponseEntity<>(response.body, status);
  }

  @JsonAnyGetter
  public Map<String, Object> getBody() { return this.body; }

}
