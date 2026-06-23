package com.ejemplo.saludplus.controller;

import com.ejemplo.saludplus.model.Medico;
import com.ejemplo.saludplus.repository.MedicoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
@CrossOrigin(origins = "*")
@Tag(name = "Médicos", description = "Gestión del personal médico del hospital. "
        + "Permite administrar doctores, sus especialidades y turnos de jefatura.")
public class MedicoController {

    private final MedicoRepository medicoRepository;

    public MedicoController(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Operation(summary = "Listar todos los médicos",
            description = "Retorna la lista completa de médicos registrados con su especialidad y estado de jefe de turno.")
    @GetMapping
    public List<Medico> obtenerTodos() {
        return medicoRepository.findAll();
    }

    @Operation(summary = "Buscar médico por ID",
            description = "Busca y retorna un médico específico por su identificador único.")
    @GetMapping("/{id}")
    public ResponseEntity<Medico> obtenerPorId(
            @Parameter(description = "ID único del médico") @PathVariable int id) {
        return medicoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Registrar nuevo médico",
            description = "Crea un nuevo registro de médico en el sistema con su RUN, nombre, especialidad y si es jefe de turno.")
    @PostMapping
    public Medico crear(@RequestBody Medico medico) {
        return medicoRepository.save(medico);
    }

    @Operation(summary = "Actualizar médico",
            description = "Actualiza los datos de un médico existente identificado por su ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Medico> actualizar(
            @Parameter(description = "ID del médico a actualizar") @PathVariable int id,
            @RequestBody Medico medico) {
        if (!medicoRepository.existsById(id)) return ResponseEntity.notFound().build();
        medico.setIdMedico(id);
        return ResponseEntity.ok(medicoRepository.save(medico));
    }

    @Operation(summary = "Eliminar médico",
            description = "Elimina un médico del sistema por su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del médico a eliminar") @PathVariable int id) {
        if (!medicoRepository.existsById(id)) return ResponseEntity.notFound().build();
        medicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reporte de médicos por especialidad",
            description = "Lista todos los médicos que tienen una especialidad específica (ej: Cardiología, Pediatría, Traumatología).")
    @GetMapping("/reporte/especialidad/{especialidad}")
    public List<Medico> reportePorEspecialidad(
            @Parameter(description = "Nombre de la especialidad a filtrar") @PathVariable String especialidad) {
        return medicoRepository.findByEspecialidad(especialidad);
    }
}
