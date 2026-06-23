package com.example.citasservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "medico_nombre", nullable = false)
    private String medicoNombre;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false)
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCita estado = EstadoCita.PENDIENTE;

    private String comentarios;
}
