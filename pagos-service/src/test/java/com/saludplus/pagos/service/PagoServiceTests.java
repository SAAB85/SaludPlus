package com.saludplus.pagos.service;

import com.saludplus.pagos.model.Pago;
import com.saludplus.pagos.repository.PagoRepository;
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
public class PagoServiceTests {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoServiceImpl pagoService;

    private Pago pagoSample;

    @BeforeEach
    void setUp() {
        pagoSample = Pago.builder()
                .id(1L)
                .atencionId(1L)
                .monto(35000.0)
                .fechaPago(LocalDateTime.of(2026, 7, 9, 11, 0))
                .metodoPago("Tarjeta de Crédito")
                .estado("PAGADO")
                .build();
    }

    @Test
    @DisplayName("Dado un Pago, cuando se guarda, entonces retorna el Pago guardado con su ID")
    void alGuardar_retornaEntidadGuardada() {
        // Given
        given(pagoRepository.save(any(Pago.class))).willReturn(pagoSample);

        // When
        Pago resultado = pagoService.guardar(pagoSample);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    @Test
    @DisplayName("Dada una lista de registros, cuando se obtienen todos, retorna la lista con elementos")
    void alObtenerTodos_retornaListaDeEntidades() {
        // Given
        given(pagoRepository.findAll()).willReturn(Collections.singletonList(pagoSample));

        // When
        List<Pago> resultado = pagoService.obtenerTodos();

        // Then
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isEqualTo(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
        verify(pagoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Dado un ID existente, cuando se busca por ID, retorna la entidad correspondiente")
    void alBuscarPorIdExistente_retornaEntidad() {
        // Given
        given(pagoRepository.findById(1L)).willReturn(Optional.of(pagoSample));

        // When
        Optional<Pago> resultado = pagoService.buscarPorId(1L);

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(1L);
        verify(pagoRepository, times(1)).findById(1L);
    }
}
