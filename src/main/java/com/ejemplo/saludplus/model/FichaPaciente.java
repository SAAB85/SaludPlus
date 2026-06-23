package com.ejemplo.saludplus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ficha_paciente")
@Schema(description = "Modelo de datos que representa la Ficha Clínica o historial del paciente")
public class FichaPaciente {

    /** id_paciente es PK y FK a la vez (@OneToOne) */
    @Id
    @Column(name = "id_paciente")
    @Schema(description = "Identificador único de la ficha clínica (corresponde al ID del Paciente)", example = "101")
    private int idPaciente;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id_paciente")
    @JsonIgnoreProperties({"fichaPaciente", "atenciones", "hibernateLazyInitializer"})
    @Schema(description = "Paciente asociado a esta ficha clínica")
    private Paciente paciente;

    @Column(name = "datos_personales")
    @Schema(description = "Campo libre 1 para datos personales o antecedentes relevantes", example = "Alergia a la Penicilina")
    private String datosPersonales;

    @Column(name = "datos_personales_2")
    @Schema(description = "Campo libre 2 para datos personales o antecedentes relevantes", example = "Hipertensión arterial controlada")
    private String datosPersonales2;

    @Column(name = "datos_personales_3")
    @Schema(description = "Campo libre 3 para datos personales o antecedentes relevantes", example = "Operación de apendicitis en 2018")
    private String datosPersonales3;

    @Column(name = "datos_personales_4")
    @Schema(description = "Campo libre 4 para datos personales o antecedentes relevantes", example = "Grupo sanguíneo: O Positivo")
    private String datosPersonales4;

    @Column(name = "datos_personales_5")
    @Schema(description = "Campo libre 5 para datos personales o antecedentes relevantes", example = "Antecedentes de asma bronquial infantil")
    private String datosPersonales5;
}
