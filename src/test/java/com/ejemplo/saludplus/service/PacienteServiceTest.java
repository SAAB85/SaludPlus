package com.ejemplo.saludplus.service;

import com.ejemplo.saludplus.model.Paciente;
import com.ejemplo.saludplus.model.TipoUsuario;
import com.ejemplo.saludplus.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias - PacienteService")
class PacienteServiceTest {

    @Mock
    private PacienteRepository repository;

    @InjectMocks
    private PacienteService service;

    private Paciente paciente;

    @BeforeEach
    void setUp() {
        TipoUsuario tipo = new TipoUsuario();
        tipo.setNombre("FONASA");

        paciente = new Paciente();
        paciente.setId(1);
        paciente.setRut("12.345.678-9");
        paciente.setNombre("Juan Pérez");
        paciente.setEdad(35);
        paciente.setTelefono("+56912345678");
        paciente.setTipoUsuario(tipo);
    }

    @Test
    @DisplayName("Debe retornar la lista de todos los pacientes")
    void testObtenerPacientes() {
        when(repository.findAll()).thenReturn(List.of(paciente));

        List<Paciente> resultado = service.obtenerPacientes();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Juan Pérez");
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar un paciente dado su ID")
    void testBuscarPorId_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(paciente));

        Paciente resultado = service.buscarPorId(1);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getRut()).isEqualTo("12.345.678-9");
        verify(repository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe retornar null si el paciente no existe")
    void testBuscarPorId_noEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        Paciente resultado = service.buscarPorId(99);

        assertThat(resultado).isNull();
    }

    @Test
    @DisplayName("Debe guardar y retornar el nuevo paciente")
    void testGuardar() {
        when(repository.save(paciente)).thenReturn(paciente);

        Paciente resultado = service.guardar(paciente);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Juan Pérez");
        verify(repository, times(1)).save(paciente);
    }

    @Test
    @DisplayName("Debe actualizar los datos del paciente")
    void testActualizar() {
        paciente.setNombre("Juan Pérez Actualizado");
        when(repository.save(paciente)).thenReturn(paciente);

        Paciente resultado = service.actualizar(1, paciente);

        assertThat(resultado.getNombre()).isEqualTo("Juan Pérez Actualizado");
        assertThat(resultado.getId()).isEqualTo(1);
        verify(repository, times(1)).save(paciente);
    }

    @Test
    @DisplayName("Debe eliminar el paciente y retornar true si existe")
    void testEliminar_exitoso() {
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);

        boolean resultado = service.eliminar(1);

        assertThat(resultado).isTrue();
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Debe retornar false si el paciente a eliminar no existe")
    void testEliminar_noExiste() {
        when(repository.existsById(99)).thenReturn(false);

        boolean resultado = service.eliminar(99);

        assertThat(resultado).isFalse();
        verify(repository, never()).deleteById(99);
    }
}
