package com.ejemplo.saludplus.controller;

import com.ejemplo.saludplus.assembler.MedicoModelAssembler;
import com.ejemplo.saludplus.model.Medico;
import com.ejemplo.saludplus.repository.MedicoRepository;
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
@RequestMapping("/api/v2/medicos")
@CrossOrigin(origins = "*")
@Tag(name = "Médicos V2 (HATEOAS)", description = "Gestión del personal médico con soporte para navegación hipermedia (HATEOAS).")
public class MedicoControllerV2 {

    private final MedicoRepository medicoRepository;
    private final MedicoModelAssembler assembler;

    public MedicoControllerV2(MedicoRepository medicoRepository, MedicoModelAssembler assembler) {
        this.medicoRepository = medicoRepository;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todos los médicos (HATEOAS)",
            description = "Retorna la lista de médicos registrados enriquecida con enlaces hipermedia de navegación.")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Medico>> obtenerTodos() {
        List<EntityModel<Medico>> medicos = medicoRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(medicos,
                linkTo(methodOn(MedicoControllerV2.class).obtenerTodos()).withSelfRel());
    }

    @Operation(summary = "Buscar médico por ID (HATEOAS)",
            description = "Busca un médico específico y retorna sus datos junto con enlaces de navegación.")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Medico>> obtenerPorId(
            @Parameter(description = "ID único del médico") @PathVariable int id) {
        return medicoRepository.findById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Registrar nuevo médico (HATEOAS)",
            description = "Crea un registro de médico y retorna el recurso con sus enlaces navegables.")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Medico>> crear(@RequestBody Medico medico) {
        Medico nuevoMedico = medicoRepository.save(medico);
        return ResponseEntity
                .created(linkTo(methodOn(MedicoControllerV2.class).obtenerPorId(nuevoMedico.getIdMedico())).toUri())
                .body(assembler.toModel(nuevoMedico));
    }

    @Operation(summary = "Actualizar médico (HATEOAS)",
            description = "Actualiza los datos del médico y retorna la entidad actualizada con sus enlaces.")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Medico>> actualizar(
            @Parameter(description = "ID del médico a actualizar") @PathVariable int id,
            @RequestBody Medico medico) {
        if (!medicoRepository.existsById(id)) return ResponseEntity.notFound().build();
        medico.setIdMedico(id);
        return ResponseEntity.ok(assembler.toModel(medicoRepository.save(medico)));
    }

    @Operation(summary = "Eliminar médico (HATEOAS)",
            description = "Elimina un médico por su ID del sistema.")
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del médico a eliminar") @PathVariable int id) {
        if (!medicoRepository.existsById(id)) return ResponseEntity.notFound().build();
        medicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reporte de médicos por especialidad (HATEOAS)",
            description = "Lista médicos filtrados por especialidad, con enlaces hipermedia.")
    @GetMapping(value = "/reporte/especialidad/{especialidad}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Medico>> reportePorEspecialidad(
            @Parameter(description = "Nombre de la especialidad a filtrar") @PathVariable String especialidad) {
        List<EntityModel<Medico>> medicos = medicoRepository.findByEspecialidad(especialidad).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(medicos,
                linkTo(methodOn(MedicoControllerV2.class).reportePorEspecialidad(especialidad)).withSelfRel(),
                linkTo(methodOn(MedicoControllerV2.class).obtenerTodos()).withRel("todos-los-medicos"));
    }
}
