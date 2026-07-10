package com.saludplus.notificaciones.service;

import com.saludplus.notificaciones.model.Notificacion;
import com.saludplus.notificaciones.repository.NotificacionRepository;
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
public class NotificacionServiceTests {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionServiceImpl notificacionService;

    private Notificacion notificacionSample;

    @BeforeEach
    void setUp() {
        notificacionSample = Notificacion.builder()
                .id(1L)
                .pacienteId(1L)
                .mensaje("Estimado Juan, le recordamos su cita el día de mañana a las 10:30 AM.")
                .fechaEnvio(LocalDateTime.of(2026, 7, 8, 18, 0))
                .canal("SMS")
                .leido(false)
                .build();
    }

    @Test
    @DisplayName("Dado un Notificacion, cuando se guarda, entonces retorna el Notificacion guardado con su ID")
    void alGuardar_retornaEntidadGuardada() {
        // Given
        given(notificacionRepository.save(any(Notificacion.class))).willReturn(notificacionSample);

        // When
        Notificacion resultado = notificacionService.guardar(notificacionSample);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));
    }

    @Test
    @DisplayName("Dada una lista de registros, cuando se obtienen todos, retorna la lista con elementos")
    void alObtenerTodos_retornaListaDeEntidades() {
        // Given
        given(notificacionRepository.findAll()).willReturn(Collections.singletonList(notificacionSample));

        // When
        List<Notificacion> resultado = notificacionService.obtenerTodos();

        // Then
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isEqualTo(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
        verify(notificacionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Dado un ID existente, cuando se busca por ID, retorna la entidad correspondiente")
    void alBuscarPorIdExistente_retornaEntidad() {
        // Given
        given(notificacionRepository.findById(1L)).willReturn(Optional.of(notificacionSample));

        // When
        Optional<Notificacion> resultado = notificacionService.buscarPorId(1L);

        // Then
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(1L);
        verify(notificacionRepository, times(1)).findById(1L);
    }
}
