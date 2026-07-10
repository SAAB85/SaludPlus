package com.saludplus.farmacia.service;

import com.saludplus.farmacia.model.Medicamento;
import com.saludplus.farmacia.repository.MedicamentoRepository;
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
public class MedicamentoServiceTests {

    @Mock
    private MedicamentoRepository medicamentoRepository;

    @InjectMocks
    private MedicamentoServiceImpl medicamentoService;

    private Medicamento medicamentoSample;

    @BeforeEach
    void setUp() {
        medicamentoSample = Medicamento.builder()
                .id(1L)
                .nombre("Paracetamol 500mg")
                .descripcion("Analgésico y antipirético para el alivio del dolor y la fiebre.")
                .stock(250)
                .precio(1200.0)
                .laboratorio("Chile Laboratorios S.A.")
                .build();
    }

    @Test
    @DisplayName("Dado un Medicamento, cuando se guarda, entonces retorna el Medicamento guardado con su ID")
    void alGuardar_retornaEntidadGuardada() {
        // Given
        given(medicamentoRepository.save(any(Medicamento.class))).willReturn(medicamentoSample);

        // When
        Medicamento resultado = medicamentoService.guardar(medicamentoSample);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(medicamentoRepository, times(1)).save(any(Medicamento.class));
    }

    @Test
    @DisplayName("Dada una lista de registros, cuando se obtienen todos, retorna la lista con elementos")
    void alObtenerTodos_retornaListaDeEntidades() {
        // Given
        given(medicamentoRepository.findAll()).willReturn(Collections.singletonList(medicamentoSample));

        // When
        List<Medicamento> resultado = medicamentoService.obtenerTodos();

        // Then
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isEqualTo(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
        verify(medicamentoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Dado un ID existente, cuando se busca por ID, retorna la entidad correspondiente")
    void alBuscarPorIdExistente_retornaEntidad() {
        // Given
        given(medicamentoRepository.findById(1L)).willReturn(Optional.of(medicamentoSample));

        // When
        Optional<Medicamento> resultado = medicamentoService.buscarPorId(1L);

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(1L);
        verify(medicamentoRepository, times(1)).findById(1L);
    }
}
