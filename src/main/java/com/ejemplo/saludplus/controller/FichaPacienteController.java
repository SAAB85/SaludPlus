package com.ejemplo.saludplus.controller;

import com.ejemplo.saludplus.model.FichaPaciente;
import com.ejemplo.saludplus.repository.FichaPacienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fichas")
@CrossOrigin(origins = "*")
@Tag(name = "Fichas de Pacientes", description = "Gestión de fichas clínicas de los pacientes. "
        + "Cada paciente tiene una ficha única (relación 1:1) con sus datos personales extendidos.")
public class FichaPacienteController {

    private final FichaPacienteRepository fichaRepository;

    public FichaPacienteController(FichaPacienteRepository fichaRepository) {
        this.fichaRepository = fichaRepository;
    }

    @Operation(summary = "Listar todas las fichas",
            description = "Retorna la lista completa de fichas clínicas registradas en el sistema.")
    @GetMapping
    public List<FichaPaciente> obtenerTodas() {
        return fichaRepository.findAll();
    }

    @Operation(summary = "Crear nueva ficha clínica",
            description = "Crea una nueva ficha clínica asociada a un paciente con sus datos personales extendidos.")
    @PostMapping
    public FichaPaciente crear(@RequestBody FichaPaciente ficha) {
        return fichaRepository.save(ficha);
    }

    @Operation(summary = "Actualizar ficha clínica",
            description = "Actualiza los datos de la ficha clínica de un paciente existente.")
    @PutMapping("/{pacienteId}")
    public ResponseEntity<FichaPaciente> actualizar(
            @Parameter(description = "ID del paciente dueño de la ficha") @PathVariable int pacienteId,
            @RequestBody FichaPaciente ficha) {
        if (!fichaRepository.existsById(pacienteId)) return ResponseEntity.notFound().build();
        ficha.setIdPaciente(pacienteId);
        return ResponseEntity.ok(fichaRepository.save(ficha));
    }

    @Operation(summary = "Historial completo del paciente",
            description = "Retorna el detalle completo de un paciente incluyendo sus datos personales (ficha) "
                    + "y todas las atenciones recibidas. Utiliza las relaciones @OneToOne y @OneToMany de JPA.")
    @GetMapping("/reporte/paciente/{pacienteId}")
    public ResponseEntity<FichaPaciente> historialPaciente(
            @Parameter(description = "ID del paciente") @PathVariable int pacienteId) {
        return fichaRepository.findByPacienteId(pacienteId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
