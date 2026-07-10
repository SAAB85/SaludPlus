package com.saludplus.farmacia.service;

import com.saludplus.farmacia.model.Medicamento;
import java.util.List;
import java.util.Optional;

public interface MedicamentoService {
    List<Medicamento> obtenerTodos();
    Optional<Medicamento> buscarPorId(Long id);
    Medicamento guardar(Medicamento medicamento);
    Optional<Medicamento> actualizar(Long id, Medicamento medicamento);
    boolean eliminar(Long id);
}
