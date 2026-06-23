package com.example.citasservice.controller;

import com.example.citasservice.dto.PacienteDTO;
import com.example.citasservice.model.Cita;
import com.example.citasservice.service.CitaService;
import com.example.citasservice.service.PacienteClientService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaController {

    private final CitaService citaService;
    private final PacienteClientService pacienteClientService;

    public CitaController(CitaService citaService, PacienteClientService pacienteClientService) {
        this.citaService = citaService;
        this.pacienteClientService = pacienteClientService;
    }

    // ── CRUD ───────────────────────────────────────────────────────────────

    @GetMapping
    public List<Cita> obtenerTodas() {
        return citaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerPorId(@PathVariable Long id) {
        return citaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cita crear(@RequestBody Cita cita) {
        return citaService.guardar(cita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizar(@PathVariable Long id, @RequestBody Cita cita) {
        if (citaService.obtenerPorId(id).isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(citaService.actualizar(id, cita));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return citaService.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // ── REPORTES ───────────────────────────────────────────────────────────

    /** Reporte: todas las citas de un paciente (también consulta sus datos al saludplus-service) */
    @GetMapping("/reporte/paciente/{pacienteId}")
    public ResponseEntity<?> citasPorPaciente(
            @PathVariable Long pacienteId,
            @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        List<Cita> citas = citaService.obtenerPorPaciente(pacienteId);
        if (authorization != null) {
            String token = authorization.replace("Bearer ", "");
            PacienteDTO paciente = pacienteClientService.obtenerPaciente(pacienteId, token);
            return ResponseEntity.ok(new ReportePaciente(paciente, citas));
        }
        return ResponseEntity.ok(citas);
    }

    /** Reporte: citas filtradas por fecha */
    @GetMapping("/reporte/fecha/{fecha}")
    public List<Cita> citasPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        return citaService.obtenerPorFecha(fecha);
    }

    /** Reporte: citas atendidas por un médico */
    @GetMapping("/reporte/medico/{medicoNombre}")
    public List<Cita> citasPorMedico(@PathVariable String medicoNombre) {
        return citaService.obtenerPorMedico(medicoNombre);
    }

    // Inner record para reporte combinado paciente + citas
    record ReportePaciente(PacienteDTO paciente, List<Cita> citas) {}
}