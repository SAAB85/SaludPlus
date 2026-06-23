package com.ejemplo.saludplus.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "medico")
@Schema(description = "Modelo de datos que representa a un Médico en el sistema")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    @Schema(description = "Identificador único del médico en la base de datos", example = "1")
    private int idMedico;

    @Column(name = "run_medico", nullable = false, unique = true)
    @Schema(description = "RUN del médico (Rut chileno con puntos y guion)", example = "15.678.910-K")
    private String runMedico;

    @Column(name = "nombre_completo", nullable = false)
    @Schema(description = "Nombre y apellido del médico", example = "Dr. Sebastián Ugarte")
    private String nombreCompleto;

    @Column(nullable = false)
    @Schema(description = "Especialidad principal del médico", example = "Cardiología")
    private String especialidad;

    @Column(name = "jefe_turno", nullable = false)
    @Schema(description = "Indica si el médico es jefe de turno ('S' = Sí, 'N' = No)", example = "S")
    private char jefeTurno = 'N';
}
