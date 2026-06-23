package com.ejemplo.saludplus.repository;

import com.ejemplo.saludplus.model.FichaPaciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FichaPacienteRepository extends JpaRepository<FichaPaciente, Integer> {
    Optional<FichaPaciente> findByPacienteId(int pacienteId);
}
