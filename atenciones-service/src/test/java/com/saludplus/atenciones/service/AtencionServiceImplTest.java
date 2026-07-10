package com.saludplus.atenciones.service;

import com.saludplus.atenciones.model.Atencion;
import com.saludplus.atenciones.repository.AtencionRepository;
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
@DisplayName("Pruebas unitarias - AtencionServiceImpl")
class AtencionServiceImplTest {

    @Mock
    private AtencionRepository atencionRepository;

    @InjectMocks
    private AtencionServiceImpl atencionService;

    private Atencion atencion;

    @BeforeEach
    void setUp() {
        atencion = new Atencion();
        atencion.setId(1L);
        atencion.setPacienteId(1L);
        atencion.setMedicoId(1L);
        atencion.setDiagnostico("Gripe");
    }

    @Test
    @DisplayName("Debe retornar todas las atenciones")
    void obtenerTodos_debeRetornarLista() {
        when(atencionRepository.findAll()).thenReturn(Arrays.asList(atencion));
        List<Atencion> resultado = atencionService.obtenerTodos();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Debe retornar atencion por ID existente")
    void buscarPorId_conIdExistente_debeRetornarAtencion() {
        when(atencionRepository.findById(1L)).thenReturn(Optional.of(atencion));
        Optional<Atencion> resultado = atencionService.buscarPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Gripe", resultado.get().getDiagnostico());
    }

    @Test
    @DisplayName("Debe retornar vacio cuando ID no existe")
    void buscarPorId_conIdInexistente_debeRetornarVacio() {
        when(atencionRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Atencion> resultado = atencionService.buscarPorId(99L);
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Debe guardar y retornar la atencion")
    void guardar_debeGuardarAtencion() {
        when(atencionRepository.save(any(Atencion.class))).thenReturn(atencion);
        Atencion resultado = atencionService.guardar(atencion);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getPacienteId());
    }

    @Test
    @DisplayName("Debe eliminar atencion existente y retornar true")
    void eliminar_conIdExistente_debeRetornarTrue() {
        when(atencionRepository.findById(1L)).thenReturn(Optional.of(atencion));
        doNothing().when(atencionRepository).delete(atencion);
        boolean resultado = atencionService.eliminar(1L);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Debe retornar false al eliminar ID inexistente")
    void eliminar_conIdInexistente_debeRetornarFalse() {
        when(atencionRepository.findById(99L)).thenReturn(Optional.empty());
        boolean resultado = atencionService.eliminar(99L);
        assertFalse(resultado);
    }
}
