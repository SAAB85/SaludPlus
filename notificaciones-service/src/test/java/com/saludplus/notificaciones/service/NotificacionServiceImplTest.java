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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias - NotificacionServiceImpl")
class NotificacionServiceImplTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionServiceImpl notificacionService;

    private Notificacion notificacion;

    @BeforeEach
    void setUp() {
        notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setPacienteId(1L);
        notificacion.setMensaje("Su cita es manana a las 10:00");
        notificacion.setCanal("EMAIL");
        notificacion.setLeido(false);
    }

    @Test
    @DisplayName("Debe retornar todas las notificaciones")
    void obtenerTodos_debeRetornarLista() {
        when(notificacionRepository.findAll()).thenReturn(Arrays.asList(notificacion));
        List<Notificacion> resultado = notificacionService.obtenerTodos();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Debe retornar notificacion por ID existente")
    void buscarPorId_conIdExistente_debeRetornarNotificacion() {
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacion));
        Optional<Notificacion> resultado = notificacionService.buscarPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("EMAIL", resultado.get().getCanal());
    }

    @Test
    @DisplayName("Debe retornar vacio cuando ID no existe")
    void buscarPorId_conIdInexistente_debeRetornarVacio() {
        when(notificacionRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Notificacion> resultado = notificacionService.buscarPorId(99L);
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Debe guardar y retornar la notificacion")
    void guardar_debeGuardarNotificacion() {
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacion);
        Notificacion resultado = notificacionService.guardar(notificacion);
        assertNotNull(resultado);
        assertFalse(resultado.getLeido());
    }

    @Test
    @DisplayName("Debe eliminar notificacion existente y retornar true")
    void eliminar_conIdExistente_debeRetornarTrue() {
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacion));
        doNothing().when(notificacionRepository).delete(notificacion);
        boolean resultado = notificacionService.eliminar(1L);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Debe retornar false al eliminar ID inexistente")
    void eliminar_conIdInexistente_debeRetornarFalse() {
        when(notificacionRepository.findById(99L)).thenReturn(Optional.empty());
        boolean resultado = notificacionService.eliminar(99L);
        assertFalse(resultado);
    }
}
