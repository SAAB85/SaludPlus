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
public class MedicoServiceTests {

    @Mock
    private MedicoRepository medicoRepository;

    @InjectMocks
    private MedicoServiceImpl medicoService;

    private Medico medicoSample;

    @BeforeEach
    void setUp() {
        medicoSample = Medico.builder()
                .id(1L)
                .rut("9876543-2")
                .nombre("Carlos")
                .apellido("Soto")
                .especialidad("Cardiología")
                .email("carlos.soto@saludplus.cl")
                .telefono("+56987654321")
                .licenciaMedica("LIC-4523-SP")
                .build();
    }

    @Test
    @DisplayName("Dado un Medico, cuando se guarda, entonces retorna el Medico guardado con su ID")
    void alGuardar_retornaEntidadGuardada() {
        // Given
        given(medicoRepository.save(any(Medico.class))).willReturn(medicoSample);

        // When
        Medico resultado = medicoService.guardar(medicoSample);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(medicoRepository, times(1)).save(any(Medico.class));
    }

    @Test
    @DisplayName("Dada una lista de registros, cuando se obtienen todos, retorna la lista con elementos")
    void alObtenerTodos_retornaListaDeEntidades() {
        // Given
        given(medicoRepository.findAll()).willReturn(Collections.singletonList(medicoSample));

        // When
        List<Medico> resultado = medicoService.obtenerTodos();

        // Then
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isEqualTo(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
        verify(medicoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Dado un ID existente, cuando se busca por ID, retorna la entidad correspondiente")
    void alBuscarPorIdExistente_retornaEntidad() {
        // Given
        given(medicoRepository.findById(1L)).willReturn(Optional.of(medicoSample));

        // When
        Optional<Medico> resultado = medicoService.buscarPorId(1L);

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(1L);
        verify(medicoRepository, times(1)).findById(1L);
    }
}
