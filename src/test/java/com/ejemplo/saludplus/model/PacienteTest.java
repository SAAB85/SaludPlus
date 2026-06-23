package com.ejemplo.saludplus.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Pruebas unitarias - Modelo Paciente")
class PacienteTest {

    private Paciente paciente;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setId(1);
        paciente.setRut("19.876.543-2");
        paciente.setNombre("María López");
        paciente.setEdad(28);
        paciente.setTelefono("+56987654321");
    }

    @Test
    @DisplayName("Debe asignar y obtener el RUT correctamente")
    void testRut() {
        assertThat(paciente.getRut()).isEqualTo("19.876.543-2");
    }

    @Test
    @DisplayName("Debe asignar y obtener el nombre correctamente")
    void testNombre() {
        assertThat(paciente.getNombre()).isEqualTo("María López");
    }

    @Test
    @DisplayName("Debe asignar y obtener la edad correctamente")
    void testEdad() {
        assertThat(paciente.getEdad()).isEqualTo(28);
    }

    @Test
    @DisplayName("Debe asignar y obtener el teléfono correctamente")
    void testTelefono() {
        assertThat(paciente.getTelefono()).startsWith("+569");
    }

    @Test
    @DisplayName("Debe asignar un TipoUsuario al paciente")
    void testTipoUsuario() {
        TipoUsuario tipo = new TipoUsuario();
        tipo.setNombre("ISAPRE");
        paciente.setTipoUsuario(tipo);

        assertThat(paciente.getTipoUsuario()).isNotNull();
        assertThat(paciente.getTipoUsuario().getNombre()).isEqualTo("ISAPRE");
    }
}
