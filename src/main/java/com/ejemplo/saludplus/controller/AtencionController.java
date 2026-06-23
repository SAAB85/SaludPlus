package com.ejemplo.saludplus.controller;

import com.ejemplo.saludplus.model.Atencion;
import com.ejemplo.saludplus.repository.AtencionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/atenciones")
@CrossOrigin(origins = "*")
@Tag(name = "Atenciones", description = "Gestión de atenciones médicas del hospital. "
        + "Registra cada visita clínica asociando un paciente con un médico, incluyendo fecha, hora, costo y comentarios.")
public class AtencionController {

    private final AtencionRepository atencionRepository;

    public AtencionController(AtencionRepository atencionRepository) {
        this.atencionRepository = atencionRepository;
    }

    @Operation(summary = "Listar todas las atenciones",
            description = "Retorna la lista completa de atenciones realizadas, con datos del paciente y médico asociados.")
    @GetMapping
    public List<Atencion> obtenerTodas() {
        return atencionRepository.findAll();
    }

    @Operation(summary = "Buscar atención por ID",
            description = "Busca y retorna una atención específica por su identificador único.")
    @GetMapping("/{id}")
    public ResponseEntity<Atencion> obtenerPorId(
            @Parameter(description = "ID único de la atención") @PathVariable Long id) {
        return atencionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Registrar nueva atención",
            description = "Crea un nuevo registro de atención médica con fecha, hora, costo, paciente, médico y comentario.")
    @PostMapping
    public Atencion crear(@RequestBody Atencion atencion) {
        return atencionRepository.save(atencion);
    }

    @Operation(summary = "Actualizar atención",
            description = "Actualiza los datos de una atención existente identificada por su ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Atencion> actualizar(
            @Parameter(description = "ID de la atención a actualizar") @PathVariable Long id,
            @RequestBody Atencion atencion) {
        if (!atencionRepository.existsById(id)) return ResponseEntity.notFound().build();
        atencion.setId(id);
        return ResponseEntity.ok(atencionRepository.save(atencion));
    }

    @Operation(summary = "Eliminar atención",
            description = "Elimina una atención del sistema por su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la atención a eliminar") @PathVariable Long id) {
        if (!atencionRepository.existsById(id)) return ResponseEntity.notFound().build();
        atencionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ── REPORTES ───────────────────────────────────────────────────────────

    @Operation(summary = "Reporte de atenciones por paciente",
            description = "Lista todas las atenciones recibidas por un paciente específico, "
                    + "incluyendo fecha, hora, costo, médico responsable y comentarios.")
    @GetMapping("/reporte/paciente/{pacienteId}")
    public List<Atencion> reportePorPaciente(
            @Parameter(description = "ID del paciente") @PathVariable int pacienteId) {
        return atencionRepository.findByPacienteId(pacienteId);
    }

    @Operation(summary = "Reporte de atenciones por médico",
            description = "Lista todas las atenciones realizadas por un médico específico, "
                    + "incluyendo fecha, hora, costo, paciente atendido y comentarios.")
    @GetMapping("/reporte/medico/{medicoId}")
    public List<Atencion> reportePorMedico(
            @Parameter(description = "ID del médico") @PathVariable int medicoId) {
        return atencionRepository.findByMedicoIdMedico(medicoId);
    }

    @Operation(summary = "Reporte de atenciones por fecha",
            description = "Lista todas las atenciones realizadas en una fecha específica (formato: YYYY-MM-DD), "
                    + "con detalles del paciente y del médico.")
    @GetMapping("/reporte/fecha/{fecha}")
    public List<Atencion> reportePorFecha(
            @Parameter(description = "Fecha de atención (formato: YYYY-MM-DD)", example = "2026-06-15")
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return atencionRepository.findByFechaAtencion(fecha);
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
