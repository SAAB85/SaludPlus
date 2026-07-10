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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias - MedicamentoServiceImpl")
class MedicamentoServiceImplTest {

    @Mock
    private MedicamentoRepository medicamentoRepository;

    @InjectMocks
    private MedicamentoServiceImpl medicamentoService;

    private Medicamento medicamento;

    @BeforeEach
    void setUp() {
        medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNombre("Paracetamol");
        medicamento.setDescripcion("Analgesico y antipiretico");
        medicamento.setStock(100);
        medicamento.setPrecio(5000.0);
        medicamento.setLaboratorio("Laboratorio Chile");
    }

    @Test
    @DisplayName("Debe retornar todos los medicamentos")
    void obtenerTodos_debeRetornarLista() {
        when(medicamentoRepository.findAll()).thenReturn(Arrays.asList(medicamento));
        List<Medicamento> resultado = medicamentoService.obtenerTodos();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Debe retornar medicamento por ID existente")
    void buscarPorId_conIdExistente_debeRetornarMedicamento() {
        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(medicamento));
        Optional<Medicamento> resultado = medicamentoService.buscarPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Paracetamol", resultado.get().getNombre());
    }

    @Test
    @DisplayName("Debe retornar vacio cuando ID no existe")
    void buscarPorId_conIdInexistente_debeRetornarVacio() {
        when(medicamentoRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Medicamento> resultado = medicamentoService.buscarPorId(99L);
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Debe guardar y retornar el medicamento")
    void guardar_debeGuardarMedicamento() {
        when(medicamentoRepository.save(any(Medicamento.class))).thenReturn(medicamento);
        Medicamento resultado = medicamentoService.guardar(medicamento);
        assertNotNull(resultado);
        assertEquals(100, resultado.getStock());
    }

    @Test
    @DisplayName("Debe eliminar medicamento existente y retornar true")
    void eliminar_conIdExistente_debeRetornarTrue() {
        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(medicamento));
        doNothing().when(medicamentoRepository).delete(medicamento);
        boolean resultado = medicamentoService.eliminar(1L);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Debe retornar false al eliminar ID inexistente")
    void eliminar_conIdInexistente_debeRetornarFalse() {
        when(medicamentoRepository.findById(99L)).thenReturn(Optional.empty());
        boolean resultado = medicamentoService.eliminar(99L);
        assertFalse(resultado);
    }
}
