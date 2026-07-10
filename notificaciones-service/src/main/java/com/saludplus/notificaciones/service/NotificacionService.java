package com.saludplus.notificaciones.service;

import com.saludplus.notificaciones.model.Notificacion;
import java.util.List;
import java.util.Optional;

public interface NotificacionService {
    List<Notificacion> obtenerTodos();
    Optional<Notificacion> buscarPorId(Long id);
    Notificacion guardar(Notificacion notificacion);
    Optional<Notificacion> actualizar(Long id, Notificacion notificacion);
    boolean eliminar(Long id);
}
