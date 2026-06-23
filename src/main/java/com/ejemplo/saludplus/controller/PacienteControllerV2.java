package com.ejemplo.saludplus.controller;

import com.ejemplo.saludplus.assembler.PacienteModelAssembler;
import com.ejemplo.saludplus.model.Paciente;
import com.ejemplo.saludplus.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/pacientes")
@CrossOrigin(origins = "*")
@Tag(name = "Pacientes V2 (HATEOAS)", description = "Gestión de pacientes con soporte para navegación hipermedia (HATEOAS).")
public class PacienteControllerV2 {

    private final PacienteService service;
    private final PacienteModelAssembler assembler;

    public PacienteControllerV2(PacienteService service, PacienteModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todos los pacientes (HATEOAS)",
            description = "Retorna la lista de pacientes registrados enriquecida con enlaces hipermedia de navegación.")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Paciente>> obtenerPacientes() {
        List<EntityModel<Paciente>> pacientes = service.obtenerPacientes().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pacientes,
                linkTo(methodOn(PacienteControllerV2.class).obtenerPacientes()).withSelfRel());
    }

    @Operation(summary = "Buscar paciente por ID (HATEOAS)",
            description = "Busca un paciente específico y retorna sus datos junto con enlaces para ver su ficha clínica y atenciones.")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Paciente>> buscarPorId(
            @Parameter(description = "ID del paciente") @PathVariable int id) {
        Paciente paciente = service.buscarPorId(id);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(paciente));
    }

    @Operation(summary = "Registrar nuevo paciente (HATEOAS)",
            description = "Crea un registro de paciente y retorna el recurso con sus enlaces navegables.")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Paciente>> guardar(@RequestBody Paciente paciente) {
        Paciente nuevoPaciente = service.guardar(paciente);
        return ResponseEntity
                .created(linkTo(methodOn(PacienteControllerV2.class).buscarPorId(nuevoPaciente.getId())).toUri())
                .body(assembler.toModel(nuevoPaciente));
    }

    @Operation(summary = "Actualizar paciente (HATEOAS)",
            description = "Actualiza los datos del paciente y retorna la entidad actualizada con enlaces actualizados.")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Paciente>> actualizar(
            @Parameter(description = "ID del paciente a actualizar") @PathVariable int id,
            @RequestBody Paciente paciente) {
        Paciente pacienteExistente = service.buscarPorId(id);
        if (pacienteExistente == null) {
            return ResponseEntity.notFound().build();
        }
        Paciente pacienteActualizado = service.actualizar(id, paciente);
        return ResponseEntity.ok(assembler.toModel(pacienteActualizado));
    }

    @Operation(summary = "Eliminar paciente (HATEOAS)",
            description = "Elimina un paciente por su ID del sistema.")
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del paciente a eliminar") @PathVariable int id) {
        boolean eliminado = service.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
