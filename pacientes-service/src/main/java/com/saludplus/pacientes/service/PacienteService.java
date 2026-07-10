package com.saludplus.pacientes.service;

import com.saludplus.pacientes.model.Paciente;
import java.util.List;
import java.util.Optional;

public interface PacienteService {
    List<Paciente> obtenerTodos();
    Optional<Paciente> buscarPorId(Long id);
    Paciente guardar(Paciente paciente);
    Optional<Paciente> actualizar(Long id, Paciente paciente);
    boolean eliminar(Long id);
}
