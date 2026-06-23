package com.ejemplo.saludplus.repository;

import com.ejemplo.saludplus.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Integer> {
    List<Medico> findByEspecialidad(String especialidad);
    List<Medico> findByJefeTurno(char jefeTurno);
}

