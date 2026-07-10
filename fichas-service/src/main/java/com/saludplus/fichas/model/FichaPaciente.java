package com.saludplus.fichas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "fichapacientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FichaPaciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pacienteId;

    private String historialMedico;

    private String alergias;

    private String grupoSanguineo;

    private LocalDateTime fechaCreacion;

    private LocalDateTime ultimaActualizacion;
}
