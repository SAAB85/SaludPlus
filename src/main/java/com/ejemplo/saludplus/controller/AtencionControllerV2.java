package com.ejemplo.saludplus.controller;

import com.ejemplo.saludplus.assembler.AtencionModelAssembler;
import com.ejemplo.saludplus.model.Atencion;
import com.ejemplo.saludplus.repository.AtencionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/atenciones")
@CrossOrigin(origins = "*")
@Tag(name = "Atenciones V2 (HATEOAS)", description = "Gestión de atenciones médicas con soporte para navegación hipermedia (HATEOAS).")
public class AtencionControllerV2 {

    private final AtencionRepository atencionRepository;
    private final AtencionModelAssembler assembler;

    public AtencionControllerV2(AtencionRepository atencionRepository, AtencionModelAssembler assembler) {
        this.atencionRepository = atencionRepository;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar todas las atenciones (HATEOAS)",
            description = "Retorna la lista completa de atenciones enriquecida con enlaces hipermedia de navegación.")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Atencion>> obtenerTodas() {
        List<EntityModel<Atencion>> atenciones = atencionRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(atenciones,
                linkTo(methodOn(AtencionControllerV2.class).obtenerTodas()).withSelfRel());
    }

    @Operation(summary = "Buscar atención por ID (HATEOAS)",
            description = "Busca una atención específica y retorna sus datos con enlaces navegables.")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Atencion>> obtenerPorId(
            @Parameter(description = "ID único de la atención") @PathVariable Long id) {
        return atencionRepository.findById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Registrar nueva atención (HATEOAS)",
            description = "Crea un registro de atención y retorna el recurso con sus enlaces navegables.")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Atencion>> crear(@RequestBody Atencion atencion) {
        Atencion nuevaAtencion = atencionRepository.save(atencion);
        return ResponseEntity
                .created(linkTo(methodOn(AtencionControllerV2.class).obtenerPorId(nuevaAtencion.getId())).toUri())
                .body(assembler.toModel(nuevaAtencion));
    }

    @Operation(summary = "Actualizar atención (HATEOAS)",
            description = "Actualiza los datos de una atención y retorna la entidad actualizada con sus enlaces.")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Atencion>> actualizar(
            @Parameter(description = "ID de la atención a actualizar") @PathVariable Long id,
            @RequestBody Atencion atencion) {
        if (!atencionRepository.existsById(id)) return ResponseEntity.notFound().build();
        atencion.setId(id);
        return ResponseEntity.ok(assembler.toModel(atencionRepository.save(atencion)));
    }

    @Operation(summary = "Eliminar atención (HATEOAS)",
            description = "Elimina una atención por su ID del sistema.")
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la atención a eliminar") @PathVariable Long id) {
        if (!atencionRepository.existsById(id)) return ResponseEntity.notFound().build();
        atencionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ── REPORTES ───────────────────────────────────────────────────────────

    @Operation(summary = "Reporte de atenciones por paciente (HATEOAS)",
            description = "Lista todas las atenciones de un paciente específico con enlaces hipermedia.")
    @GetMapping(value = "/reporte/paciente/{pacienteId}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Atencion>> reportePorPaciente(
            @Parameter(description = "ID del paciente") @PathVariable int pacienteId) {
        List<EntityModel<Atencion>> atenciones = atencionRepository.findByPacienteId(pacienteId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(atenciones,
                linkTo(methodOn(AtencionControllerV2.class).reportePorPaciente(pacienteId)).withSelfRel(),
                linkTo(methodOn(AtencionControllerV2.class).obtenerTodas()).withRel("todas-las-atenciones"));
    }

    @Operation(summary = "Reporte de atenciones por médico (HATEOAS)",
            description = "Lista todas las atenciones realizadas por un médico específico con enlaces hipermedia.")
    @GetMapping(value = "/reporte/medico/{medicoId}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Atencion>> reportePorMedico(
            @Parameter(description = "ID del médico") @PathVariable int medicoId) {
        List<EntityModel<Atencion>> atenciones = atencionRepository.findByMedicoIdMedico(medicoId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(atenciones,
                linkTo(methodOn(AtencionControllerV2.class).reportePorMedico(medicoId)).withSelfRel(),
                linkTo(methodOn(AtencionControllerV2.class).obtenerTodas()).withRel("todas-las-atenciones"));
    }

    @Operation(summary = "Reporte de atenciones por fecha (HATEOAS)",
            description = "Lista todas las atenciones realizadas en una fecha específica (formato: YYYY-MM-DD).")
    @GetMapping(value = "/reporte/fecha/{fecha}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Atencion>> reportePorFecha(
            @Parameter(description = "Fecha de atención (formato: YYYY-MM-DD)", example = "2026-06-15")
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<EntityModel<Atencion>> atenciones = atencionRepository.findByFechaAtencion(fecha).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(atenciones,
                linkTo(methodOn(AtencionControllerV2.class).reportePorFecha(fecha)).withSelfRel(),
                linkTo(methodOn(AtencionControllerV2.class).obtenerTodas()).withRel("todas-las-atenciones"));
    }

    @Operation(summary = "Reporte de costos por tipo de usuario",
            description = "Retorna el total de costos de atenciones agrupados por tipo de usuario (ej: Fonasa, Isapre, Particular).")
    @GetMapping("/reporte/costos-por-tipo-usuario")
    public List<Map<String, Object>> reporteCostosPorTipoUsuario() {
        return atencionRepository.reporteCostosPorTipoUsuario()
                .stream()
                .map(row -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("tipoUsuario", row[0]);
                    item.put("totalCostos", row[1]);
                    return item;
                })
                .toList();
    }
}
