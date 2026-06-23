package com.ejemplo.saludplus.repository;

import com.ejemplo.saludplus.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
}