package com.ejemplo.saludplus.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la carga masiva o transferencia de datos de Paciente")
public class PacienteDTO {

    @Schema(description = "RUT del paciente con puntos y guion", example = "18.456.789-0")
    private String rut;

    @Schema(description = "Nombre completo del paciente", example = "Carlos Valenzuela")
    private String nombre;

    @Schema(description = "Edad del paciente en años", example = "34")
    private int edad;

    @Schema(description = "Teléfono de contacto del paciente", example = "+56911223344")
    private String telefono;

    public PacienteDTO() {
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}