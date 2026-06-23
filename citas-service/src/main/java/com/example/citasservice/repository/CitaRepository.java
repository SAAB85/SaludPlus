package com.example.citasservice.repository;

import com.example.citasservice.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByPacienteId(Long pacienteId);

    List<Cita> findByFecha(LocalDate fecha);

    List<Cita> findByMedicoNombre(String medicoNombre);
}
