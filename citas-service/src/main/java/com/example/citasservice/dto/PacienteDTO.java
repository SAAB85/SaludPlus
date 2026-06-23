package com.example.citasservice.dto;

import lombok.Data;

@Data
public class PacienteDTO {

    private int id;
    private String rut;
    private String nombre;
    private int edad;
    private String telefono;
}