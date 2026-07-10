package com.saludplus.fichas.repository;

import com.saludplus.fichas.model.FichaPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FichaPacienteRepository extends JpaRepository<FichaPaciente, Long> {
}
