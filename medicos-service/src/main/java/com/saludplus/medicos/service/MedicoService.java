package com.saludplus.medicos.service;

import com.saludplus.medicos.model.Medico;
import java.util.List;
import java.util.Optional;

public interface MedicoService {
    List<Medico> obtenerTodos();
    Optional<Medico> buscarPorId(Long id);
    Medico guardar(Medico medico);
    Optional<Medico> actualizar(Long id, Medico medico);
    boolean eliminar(Long id);
}
