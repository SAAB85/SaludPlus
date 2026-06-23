package com.ejemplo.saludplus.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tipo_usuario")
@Schema(description = "Modelo de datos que representa el Tipo de Usuario o Previsión del paciente")
public class TipoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del tipo de usuario", example = "2")
    private int id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre del tipo de usuario / convenio de salud", example = "FONASA")
    private String nombre;
}
