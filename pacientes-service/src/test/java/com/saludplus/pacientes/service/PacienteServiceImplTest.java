package com.saludplus.pacientes.service;

import com.saludplus.pacientes.model.Paciente;
import com.saludplus.pacientes.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias - PacienteServiceImpl")
class PacienteServiceImplTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    private Paciente paciente;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setRut("12345678-9");
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setEmail("juan@test.cl");
        paciente.setTelefono("+56912345678");
    }

    @Test
    @DisplayName("Debe retornar lista de todos los pacientes")
    void obtenerTodos_debeRetornarListaPacientes() {
        // Given
        when(pacienteRepository.findAll()).thenReturn(Arrays.asList(paciente));
        // When
        List<Paciente> resultado = pacienteService.obtenerTodos();
        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(pacienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar paciente por ID existente")
    void buscarPorId_conIdExistente_debeRetornarPaciente() {
        // Given
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        // When
        Optional<Paciente> resultado = pacienteService.buscarPorId(1L);
        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
    }

    @Test
    @DisplayName("Debe retornar vacío cuando ID no existe")
    void buscarPorId_conIdInexistente_debeRetornarVacio() {
        // Given
        when(pacienteRepository.findById(99L)).thenReturn(Optional.empty());
        // When
        Optional<Paciente> resultado = pacienteService.buscarPorId(99L);
        // Then
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Debe guardar y retornar el paciente")
    void guardar_debeGuardarPacienteCorrectamente() {
        // Given
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);
        // When
        Paciente resultado = pacienteService.guardar(paciente);
        // Then
        assertNotNull(resultado);
        assertEquals("12345678-9", resultado.getRut());
    }

    @Test
    @DisplayName("Debe eliminar paciente existente y retornar true")
    void eliminar_conIdExistente_debeRetornarTrue() {
        // Given
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        doNothing().when(pacienteRepository).delete(paciente);
        // When
        boolean resultado = pacienteService.eliminar(1L);
        // Then
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Debe retornar false al eliminar ID inexistente")
    void eliminar_conIdInexistente_debeRetornarFalse() {
        // Given
        when(pacienteRepository.findById(99L)).thenReturn(Optional.empty());
        // When
        boolean resultado = pacienteService.eliminar(99L);
        // Then
        assertFalse(resultado);
    }
}
