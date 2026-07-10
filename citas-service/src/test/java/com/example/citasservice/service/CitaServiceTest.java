package com.example.citasservice.service;

import com.example.citasservice.model.Cita;
import com.example.citasservice.repository.CitaRepository;
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
@DisplayName("Pruebas unitarias - CitaService")
class CitaServiceTest {

    @Mock
    private CitaRepository citaRepository;

    @InjectMocks
    private CitaService citaService;

    private Cita cita;

    @BeforeEach
    void setUp() {
        cita = new Cita();
        cita.setId(1L);
        cita.setPacienteId(1L);
        cita.setMedicoNombre("Dr. Lopez");
    }

    @Test
    @DisplayName("Debe retornar todas las citas")
    void obtenerTodas_debeRetornarLista() {
        when(citaRepository.findAll()).thenReturn(Arrays.asList(cita));
        List<Cita> resultado = citaService.obtenerTodas();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Debe retornar cita por ID existente")
    void obtenerPorId_conIdExistente_debeRetornarCita() {
        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));
        Optional<Cita> resultado = citaService.obtenerPorId(1L);
        assertTrue(resultado.isPresent());
    }

    @Test
    @DisplayName("Debe retornar vacio cuando ID no existe")
    void obtenerPorId_conIdInexistente_debeRetornarVacio() {
        when(citaRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Cita> resultado = citaService.obtenerPorId(99L);
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Debe guardar y retornar la cita")
    void guardar_debeGuardarCita() {
        when(citaRepository.save(any(Cita.class))).thenReturn(cita);
        Cita resultado = citaService.guardar(cita);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getPacienteId());
    }

    @Test
    @DisplayName("Debe eliminar cita existente y retornar true")
    void eliminar_conIdExistente_debeRetornarTrue() {
        when(citaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(citaRepository).deleteById(1L);
        boolean resultado = citaService.eliminar(1L);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Debe retornar false al eliminar ID inexistente")
    void eliminar_conIdInexistente_debeRetornarFalse() {
        when(citaRepository.existsById(99L)).thenReturn(false);
        boolean resultado = citaService.eliminar(99L);
        assertFalse(resultado);
    }
}
