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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTests {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    private Paciente pacienteSample;

    @BeforeEach
    void setUp() {
        pacienteSample = Paciente.builder()
                .id(1L)
                .rut("12345678-9")
                .nombre("Juan")
                .apellido("Pérez")
                .email("juan.perez@saludplus.cl")
                .telefono("+56912345678")
                .fechaNacimiento(LocalDate.of(1990, 5, 15))
                .obraSocial("Fonasa")
                .build();
    }

    @Test
    @DisplayName("Dado un Paciente, cuando se guarda, entonces retorna el Paciente guardado con su ID")
    void alGuardar_retornaEntidadGuardada() {
        // Given
        given(pacienteRepository.save(any(Paciente.class))).willReturn(pacienteSample);

        // When
        Paciente resultado = pacienteService.guardar(pacienteSample);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    @DisplayName("Dada una lista de registros, cuando se obtienen todos, retorna la lista con elementos")
    void alObtenerTodos_retornaListaDeEntidades() {
        // Given
        given(pacienteRepository.findAll()).willReturn(Collections.singletonList(pacienteSample));

        // When
        List<Paciente> resultado = pacienteService.obtenerTodos();

        // Then
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isEqualTo(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
        verify(pacienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Dado un ID existente, cuando se busca por ID, retorna la entidad correspondiente")
    void alBuscarPorIdExistente_retornaEntidad() {
        // Given
        given(pacienteRepository.findById(1L)).willReturn(Optional.of(pacienteSample));

        // When
        Optional<Paciente> resultado = pacienteService.buscarPorId(1L);

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(1L);
        verify(pacienteRepository, times(1)).findById(1L);
    }
}
