package com.saludplus.medicos.service;

import com.saludplus.medicos.model.Medico;
import com.saludplus.medicos.repository.MedicoRepository;
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
@DisplayName("Pruebas unitarias - MedicoServiceImpl")
class MedicoServiceImplTest {

    @Mock
    private MedicoRepository medicoRepository;

    @InjectMocks
    private MedicoServiceImpl medicoService;

    private Medico medico;

    @BeforeEach
    void setUp() {
        medico = new Medico();
        medico.setId(1L);
        medico.setRut("98765432-1");
        medico.setNombre("Carlos");
        medico.setApellido("Lopez");
        medico.setEspecialidad("Cardiologia");
        medico.setEmail("carlos@saludplus.cl");
        medico.setTelefono("+56987654321");
        medico.setLicenciaMedica("LIC-001");
    }

    @Test
    @DisplayName("Debe retornar lista de todos los medicos")
    void obtenerTodos_debeRetornarListaMedicos() {
        // Given
        when(medicoRepository.findAll()).thenReturn(Arrays.asList(medico));
        // When
        List<Medico> resultado = medicoService.obtenerTodos();
        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(medicoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar medico por ID existente")
    void buscarPorId_conIdExistente_debeRetornarMedico() {
        // Given
        when(medicoRepository.findById(1L)).thenReturn(Optional.of(medico));
        // When
        Optional<Medico> resultado = medicoService.buscarPorId(1L);
        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Carlos", resultado.get().getNombre());
    }

    @Test
    @DisplayName("Debe retornar vacio cuando ID no existe")
    void buscarPorId_conIdInexistente_debeRetornarVacio() {
        // Given
        when(medicoRepository.findById(99L)).thenReturn(Optional.empty());
        // When
        Optional<Medico> resultado = medicoService.buscarPorId(99L);
        // Then
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Debe guardar y retornar el medico")
    void guardar_debeGuardarMedicoCorrectamente() {
        // Given
        when(medicoRepository.save(any(Medico.class))).thenReturn(medico);
        // When
        Medico resultado = medicoService.guardar(medico);
        // Then
        assertNotNull(resultado);
        assertEquals("Cardiologia", resultado.getEspecialidad());
    }

    @Test
    @DisplayName("Debe eliminar medico existente y retornar true")
    void eliminar_conIdExistente_debeRetornarTrue() {
        // Given
        when(medicoRepository.findById(1L)).thenReturn(Optional.of(medico));
        doNothing().when(medicoRepository).delete(medico);
        // When
        boolean resultado = medicoService.eliminar(1L);
        // Then
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Debe retornar false al eliminar ID inexistente")
    void eliminar_conIdInexistente_debeRetornarFalse() {
        // Given
        when(medicoRepository.findById(99L)).thenReturn(Optional.empty());
        // When
        boolean resultado = medicoService.eliminar(99L);
        // Then
        assertFalse(resultado);
    }
}
