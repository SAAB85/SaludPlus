package com.saludplus.fichas.service;

import com.saludplus.fichas.model.FichaPaciente;
import com.saludplus.fichas.repository.FichaPacienteRepository;
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
public class FichaPacienteServiceTests {

    @Mock
    private FichaPacienteRepository fichaPacienteRepository;

    @InjectMocks
    private FichaPacienteServiceImpl fichaPacienteService;

    private FichaPaciente fichaPacienteSample;

    @BeforeEach
    void setUp() {
        fichaPacienteSample = FichaPaciente.builder()
                .id(1L)
                .pacienteId(1L)
                .historialMedico("Asma infantil, cirugía de apéndice en 2018. Hipertenso en tratamiento.")
                .alergias("Penicilina, polen de plátano oriental")
                .grupoSanguineo("O-Positivo")
                .fechaCreacion(LocalDateTime.of(2024, 1, 10, 9, 0))
                .ultimaActualizacion(LocalDateTime.of(2026, 7, 9, 14, 0))
                .build();
    }

    @Test
    @DisplayName("Dado un FichaPaciente, cuando se guarda, entonces retorna el FichaPaciente guardado con su ID")
    void alGuardar_retornaEntidadGuardada() {
        // Given
        given(fichaPacienteRepository.save(any(FichaPaciente.class))).willReturn(fichaPacienteSample);

        // When
        FichaPaciente resultado = fichaPacienteService.guardar(fichaPacienteSample);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(fichaPacienteRepository, times(1)).save(any(FichaPaciente.class));
    }

    @Test
    @DisplayName("Dada una lista de registros, cuando se obtienen todos, retorna la lista con elementos")
    void alObtenerTodos_retornaListaDeEntidades() {
        // Given
        given(fichaPacienteRepository.findAll()).willReturn(Collections.singletonList(fichaPacienteSample));

        // When
        List<FichaPaciente> resultado = fichaPacienteService.obtenerTodos();

        // Then
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isEqualTo(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
        verify(fichaPacienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Dado un ID existente, cuando se busca por ID, retorna la entidad correspondiente")
    void alBuscarPorIdExistente_retornaEntidad() {
        // Given
        given(fichaPacienteRepository.findById(1L)).willReturn(Optional.of(fichaPacienteSample));

        // When
        Optional<FichaPaciente> resultado = fichaPacienteService.buscarPorId(1L);

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(1L);
        verify(fichaPacienteRepository, times(1)).findById(1L);
    }
}
