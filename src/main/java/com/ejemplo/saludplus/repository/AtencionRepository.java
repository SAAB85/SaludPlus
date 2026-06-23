package com.ejemplo.saludplus.repository;

import com.ejemplo.saludplus.model.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AtencionRepository extends JpaRepository<Atencion, Long> {

    // Reporte: atenciones por paciente
    List<Atencion> findByPacienteId(int pacienteId);

    // Reporte: atenciones por médico
    List<Atencion> findByMedicoIdMedico(int idMedico);

    // Reporte: atenciones por fecha
    List<Atencion> findByFechaAtencion(LocalDate fechaAtencion);

    // Reporte: total costos agrupados por tipo de usuario
    @Query("SELECT p.tipoUsuario.nombre, SUM(a.costo) " +
           "FROM Atencion a JOIN a.paciente p " +
           "GROUP BY p.tipoUsuario.nombre")
    List<Object[]> reporteCostosPorTipoUsuario();
}

