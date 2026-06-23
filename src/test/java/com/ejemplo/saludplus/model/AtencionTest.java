package com.ejemplo.saludplus.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Pruebas unitarias - Modelo Atencion")
class AtencionTest {

    private Atencion atencion;
    private Paciente paciente;
    private Medico medico;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setId(1);
        paciente.setNombre("Ana Torres");

        medico = new Medico();
        medico.setIdMedico(1);
        medico.setNombreCompleto("Dr. Carlos Pérez");
        medico.setEspecialidad("Pediatría");

        atencion = new Atencion();
        atencion.setId(100L);
        atencion.setPaciente(paciente);
        atencion.setMedico(medico);
        atencion.setFechaAtencion(LocalDate.of(2026, 6, 15));
        atencion.setHoraAtencion(LocalTime.of(10, 30));
        atencion.setCosto(45000);
        atencion.setComentario("Consulta de control pediátrico sin novedades.");
    }

    @Test
    @DisplayName("Debe asignar y obtener la fecha de atención correctamente")
    void testFechaAtencion() {
        assertThat(atencion.getFechaAtencion()).isEqualTo(LocalDate.of(2026, 6, 15));
    }

    @Test
    @DisplayName("Debe asignar y obtener la hora de atención correctamente")
    void testHoraAtencion() {
        assertThat(atencion.getHoraAtencion()).isEqualTo(LocalTime.of(10, 30));
    }

    @Test
    @DisplayName("El costo debe ser mayor a cero")
    void testCosto() {
        assertThat(atencion.getCosto()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Debe tener un paciente asociado")
    void testPacienteAsociado() {
        assertThat(atencion.getPaciente()).isNotNull();
        assertThat(atencion.getPaciente().getNombre()).isEqualTo("Ana Torres");
    }

    @Test
    @DisplayName("Debe tener un médico asociado")
    void testMedicoAsociado() {
        assertThat(atencion.getMedico()).isNotNull();
        assertThat(atencion.getMedico().getEspecialidad()).isEqualTo("Pediatría");
    }

    @Test
    @DisplayName("El comentario no debe estar vacío")
    void testComentario() {
        assertThat(atencion.getComentario()).isNotBlank();
    }
}
