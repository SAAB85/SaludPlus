package com.ejemplo.saludplus.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Pruebas unitarias - Modelo Medico")
class MedicoTest {

    private Medico medico;

    @BeforeEach
    void setUp() {
        medico = new Medico();
        medico.setIdMedico(1);
        medico.setRunMedico("15.678.910-K");
        medico.setNombreCompleto("Dr. Sebastián Ugarte");
        medico.setEspecialidad("Cardiología");
        medico.setJefeTurno('S');
    }

    @Test
    @DisplayName("Debe asignar y obtener el RUN del médico correctamente")
    void testRunMedico() {
        assertThat(medico.getRunMedico()).isEqualTo("15.678.910-K");
    }

    @Test
    @DisplayName("Debe asignar y obtener el nombre completo correctamente")
    void testNombreCompleto() {
        assertThat(medico.getNombreCompleto()).startsWith("Dr.");
    }

    @Test
    @DisplayName("Debe asignar y obtener la especialidad correctamente")
    void testEspecialidad() {
        assertThat(medico.getEspecialidad()).isEqualTo("Cardiología");
    }

    @Test
    @DisplayName("Debe reconocer si el médico es jefe de turno")
    void testJefeTurnoSi() {
        assertThat(medico.getJefeTurno()).isEqualTo('S');
    }

    @Test
    @DisplayName("El valor por defecto de jefe de turno debe ser 'N'")
    void testJefeTurnoPorDefecto() {
        Medico nuevoMedico = new Medico();
        assertThat(nuevoMedico.getJefeTurno()).isEqualTo('N');
    }
}
