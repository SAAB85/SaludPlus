package com.saludplus.atenciones.service;

import com.saludplus.atenciones.model.Atencion;
import java.util.List;
import java.util.Optional;

public interface AtencionService {
    List<Atencion> obtenerTodos();
    Optional<Atencion> buscarPorId(Long id);
    Atencion guardar(Atencion atencion);
    Optional<Atencion> actualizar(Long id, Atencion atencion);
    boolean eliminar(Long id);
}
