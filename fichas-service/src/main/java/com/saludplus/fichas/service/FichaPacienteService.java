package com.saludplus.fichas.service;

import com.saludplus.fichas.model.FichaPaciente;
import java.util.List;
import java.util.Optional;

public interface FichaPacienteService {
    List<FichaPaciente> obtenerTodos();
    Optional<FichaPaciente> buscarPorId(Long id);
    FichaPaciente guardar(FichaPaciente fichaPaciente);
    Optional<FichaPaciente> actualizar(Long id, FichaPaciente fichaPaciente);
    boolean eliminar(Long id);
}
