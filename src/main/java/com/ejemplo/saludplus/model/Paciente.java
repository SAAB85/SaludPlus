package com.ejemplo.saludplus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "paciente")
@Schema(description = "Modelo de datos que representa a un Paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del paciente", example = "101")
    private int id;

    @Column(unique = true, nullable = false)
    @Schema(description = "RUT del paciente (con puntos y guion)", example = "19.345.678-2")
    private String rut;

    @Column(nullable = false)
    @Schema(description = "Nombre completo del paciente", example = "María José Loyola")
    private String nombre;

    @Schema(description = "Edad del paciente", example = "28")
    private int edad;

    @Schema(description = "Número telefónico de contacto", example = "+56987654321")
    private String telefono;

    /** Relación @ManyToOne con TipoUsuario */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_usuario_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Schema(description = "Tipo de usuario/previsión asignado al paciente")
    private TipoUsuario tipoUsuario;

    /** Relación @OneToMany con Atencion */
    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"paciente", "hibernateLazyInitializer"})
    @Schema(description = "Listado de atenciones médicas que ha tenido el paciente")
    private List<Atencion> atenciones;

    /** Relación @OneToOne con FichaPaciente */
    @OneToOne(mappedBy = "paciente", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"paciente", "hibernateLazyInitializer"})
    @Schema(description = "Ficha clínica/historial clínico del paciente")
    private FichaPaciente fichaPaciente;
}