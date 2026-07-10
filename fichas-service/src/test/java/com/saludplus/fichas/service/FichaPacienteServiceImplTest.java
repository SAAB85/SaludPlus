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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias - FichaPacienteServiceImpl")
class FichaPacienteServiceImplTest {

    @Mock
    private FichaPacienteRepository fichaRepository;

    @InjectMocks
    private FichaPacienteServiceImpl fichaService;

    private FichaPaciente ficha;

    @BeforeEach
    void setUp() {
        ficha = new FichaPaciente();
        ficha.setId(1L);
        ficha.setPacienteId(1L);
        ficha.setGrupoSanguineo("O+");
        ficha.setAlergias("Penicilina");
    }

    @Test
    @DisplayName("Debe retornar todas las fichas")
    void obtenerTodos_debeRetornarLista() {
        when(fichaRepository.findAll()).thenReturn(Arrays.asList(ficha));
        List<FichaPaciente> resultado = fichaService.obtenerTodos();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Debe retornar ficha por ID existente")
    void buscarPorId_conIdExistente_debeRetornarFicha() {
        when(fichaRepository.findById(1L)).thenReturn(Optional.of(ficha));
        Optional<FichaPaciente> resultado = fichaService.buscarPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("O+", resultado.get().getGrupoSanguineo());
    }

    @Test
    @DisplayName("Debe retornar vacio cuando ID no existe")
    void buscarPorId_conIdInexistente_debeRetornarVacio() {
        when(fichaRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<FichaPaciente> resultado = fichaService.buscarPorId(99L);
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Debe guardar y retornar la ficha")
    void guardar_debeGuardarFicha() {
        when(fichaRepository.save(any(FichaPaciente.class))).thenReturn(ficha);
        FichaPaciente resultado = fichaService.guardar(ficha);
        assertNotNull(resultado);
        assertEquals("Penicilina", resultado.getAlergias());
    }

    @Test
    @DisplayName("Debe eliminar ficha existente y retornar true")
    void eliminar_conIdExistente_debeRetornarTrue() {
        when(fichaRepository.findById(1L)).thenReturn(Optional.of(ficha));
        doNothing().when(fichaRepository).delete(ficha);
        boolean resultado = fichaService.eliminar(1L);
        assertTrue(resultado);
    }

    @Test
    @DisplayName("Debe retornar false al eliminar ID inexistente")
    void eliminar_conIdInexistente_debeRetornarFalse() {
        when(fichaRepository.findById(99L)).thenReturn(Optional.empty());
        boolean resultado = fichaService.eliminar(99L);
        assertFalse(resultado);
    }
}
