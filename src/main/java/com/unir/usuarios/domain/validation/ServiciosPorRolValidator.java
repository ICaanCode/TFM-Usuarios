package com.unir.usuarios.domain.validation;

import com.unir.usuarios.api.dto.usuario.EstadoServicio;
import com.unir.usuarios.api.dto.usuario.crear.CrearUsuarioRequest;
import com.unir.usuarios.api.dto.usuario.modificar.ModificarUsuarioRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ServiciosPorRolValidator implements ConstraintValidator<ServiciosPorRolValidos, Object> {

  private static final int ROL_TURNOS = 10001;

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    if (value instanceof CrearUsuarioRequest crear) {
      return validar(crear.getCodigoRol(), crear.getServicios(), context);
    } else if (value instanceof ModificarUsuarioRequest modificar) {
      Integer rol = modificar.getCodigoRol();
      if (rol == null) return true;
      return validar(modificar.getCodigoRol(), modificar.getServicios(), context);
    }
    return true;
  }

  private boolean validar(Integer rol, List<EstadoServicio> servicios, ConstraintValidatorContext context) {
    if (rol == ROL_TURNOS && (servicios == null || servicios.isEmpty())) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Debe incluir al menos un servicio para usuarios de tipo turnos (10001).")
          .addPropertyNode("servicios")
          .addConstraintViolation();
      return false;
    }

    if (rol != ROL_TURNOS && servicios != null && !servicios.isEmpty()) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("No puede incluir servicios para usuarios que no sean de tipo turnos (10001).")
          .addPropertyNode("servicios")
          .addConstraintViolation();
      return false;
    }

    return true;
  }

}
