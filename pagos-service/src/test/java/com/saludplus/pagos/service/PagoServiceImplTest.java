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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias - PagoServiceImpl")
class PagoServiceImplTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoServiceImpl pagoService;

    private Pago pago;

    @BeforeEach
    void setUp() {
        pago = new Pago();
        pago.setId(1L);
        pago.setAtencionId(1L);
        pago.setMonto(50000.0);
        pago.setMetodoPago("Tarjeta");
        pago.setEstado("Pagado");
    }

    @Test
    @DisplayName("Debe retornar todos los pagos")
    void obtenerTodos_debeRetornarLista() {
        when(pagoRepository.findAll()).thenReturn(Arrays.asList(pago));
        List<Pago> resultado = pagoService.obtenerTodos();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Debe retornar pago por ID existente")
    void buscarPorId_conIdExistente_debeRetornarPago() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        Optional<Pago> resultado = pagoService.buscarPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Tarjeta", resultado.get().getMetodoPago());
    }

    @Test
    @DisplayName("Debe retornar vacio cuando ID no existe")
    void buscarPorId_conIdInexistente_debeRetornarVacio() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Pago> resultado = pagoService.buscarPorId(99L);
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Debe guardar y retornar el pago")
    void guardar_debeGuardarPago() {
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);
        Pago resultado = pagoService.guardar(pago);
        assertNotNull(resultado);
        assertEquals(50000.0, resultado.getMonto());
    }

    @Test
    @DisplayName("Debe eliminar pago existente y retornar true")
    void eliminar_conIdExistente_debeRetornarTrue() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        doNothing().when(pagoRepository).delete(pago);
        boolean resultado = pagoService.eliminar(1L);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Debe retornar false al eliminar ID inexistente")
    void eliminar_conIdInexistente_debeRetornarFalse() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());
        boolean resultado = pagoService.eliminar(99L);
        assertFalse(resultado);
    }
}
