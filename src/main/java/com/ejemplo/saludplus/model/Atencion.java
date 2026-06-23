package com.ejemplo.saludplus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "atencion")
@Schema(description = "Modelo de datos que representa una Atención Médica")
public class Atencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la atención médica", example = "5001")
    private Long id;

    @Column(name = "fecha_atencion", nullable = false)
    @Schema(description = "Fecha en que se realizó la atención", example = "2026-06-15")
    private LocalDate fechaAtencion;

    @Column(name = "hora_atencion", nullable = false)
    @Schema(description = "Hora en que se realizó la atención", example = "14:30:00")
    private LocalTime horaAtencion;

    @Column(nullable = false)
    @Schema(description = "Costo total de la consulta / atención médica", example = "45000.0")
    private double costo;

    /** Relación @ManyToOne con Paciente */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    @JsonIgnoreProperties({"atenciones", "fichaPaciente", "hibernateLazyInitializer"})
    @Schema(description = "Paciente atendido")
    private Paciente paciente;

    /** Relación @ManyToOne con Médico */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medico", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Schema(description = "Médico que atendió la consulta")
    private Medico medico;

    @Schema(description = "Comentario o diagnóstico general de la atención", example = "Paciente presenta síntomas de gripe común. Se receta reposo por 3 días y paracetamol.")
    private String comentario;
}
