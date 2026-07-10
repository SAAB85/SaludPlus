package com.saludplus.pagos.service;

import com.saludplus.pagos.model.Pago;
import java.util.List;
import java.util.Optional;

public interface PagoService {
    List<Pago> obtenerTodos();
    Optional<Pago> buscarPorId(Long id);
    Pago guardar(Pago pago);
    Optional<Pago> actualizar(Long id, Pago pago);
    boolean eliminar(Long id);
}
