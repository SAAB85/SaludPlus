package com.ejemplo.saludplus.controller;

import com.ejemplo.saludplus.assembler.FichaPacienteModelAssembler;
import com.ejemplo.saludplus.model.FichaPaciente;
import com.ejemplo.saludplus.repository.FichaPacienteRepository;
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
@RequestMapping("/api/v2/fichas")
@CrossOrigin(origins = "*")
@Tag(name = "Fichas de Pacientes V2 (HATEOAS)", description = "Gestión de fichas clínicas con soporte para navegación hipermedia (HATEOAS).")
public class FichaPacienteControllerV2 {

    private final FichaPacienteRepository fichaRepository;
    private final FichaPacienteModelAssembler assembler;

    public FichaPacienteControllerV2(FichaPacienteRepository fichaRepository, FichaPacienteModelAssembler assembler) {
        this.fichaRepository = fichaRepository;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todas las fichas clínicas (HATEOAS)",
            description = "Retorna la lista completa de fichas clínicas con enlaces hipermedia de navegación.")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<FichaPaciente>> obtenerTodas() {
        List<EntityModel<FichaPaciente>> fichas = fichaRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(fichas,
                linkTo(methodOn(FichaPacienteControllerV2.class).obtenerTodas()).withSelfRel());
    }

    @Operation(summary = "Crear nueva ficha clínica (HATEOAS)",
            description = "Crea una ficha clínica y retorna el recurso con sus enlaces navegables.")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<FichaPaciente>> crear(@RequestBody FichaPaciente ficha) {
        FichaPaciente nuevaFicha = fichaRepository.save(ficha);
        return ResponseEntity
                .created(linkTo(methodOn(FichaPacienteControllerV2.class).historialPaciente(nuevaFicha.getIdPaciente())).toUri())
                .body(assembler.toModel(nuevaFicha));
    }

    @Operation(summary = "Actualizar ficha clínica (HATEOAS)",
            description = "Actualiza la ficha clínica de un paciente y retorna la entidad actualizada con sus enlaces.")
    @PutMapping(value = "/{pacienteId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<FichaPaciente>> actualizar(
            @Parameter(description = "ID del paciente dueño de la ficha") @PathVariable int pacienteId,
            @RequestBody FichaPaciente ficha) {
        if (!fichaRepository.existsById(pacienteId)) return ResponseEntity.notFound().build();
        ficha.setIdPaciente(pacienteId);
        return ResponseEntity.ok(assembler.toModel(fichaRepository.save(ficha)));
    }

    @Operation(summary = "Historial completo del paciente (HATEOAS)",
            description = "Retorna la ficha clínica de un paciente con enlaces navegables al paciente y a la lista de fichas.")
    @GetMapping(value = "/reporte/paciente/{pacienteId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<FichaPaciente>> historialPaciente(
            @Parameter(description = "ID del paciente") @PathVariable int pacienteId) {
        return fichaRepository.findByPacienteId(pacienteId)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
