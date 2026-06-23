package com.example.citasservice.controller;

import com.example.citasservice.model.Cita;
import com.example.citasservice.service.CitaService;
import com.example.citasservice.service.PacienteClientService;
import com.example.citasservice.dto.PacienteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@Tag(name = "Módulo de Citas Médicas", description = "Endpoints para la gestión de citas médicas")
public class CitaController {

    private final CitaService citaService;
    private final PacienteClientService pacienteClientService;

    public CitaController(CitaService citaService, PacienteClientService pacienteClientService) {
        this.citaService = citaService;
        this.pacienteClientService = pacienteClientService;
    }

    @Operation(summary = "Obtener todas las citas", description = "Retorna el listado completo de citas registradas.")
    @GetMapping
    public List<Cita> obtenerTodas() {
        return citaService.obtenerTodas();
    }

    @Operation(summary = "Crear una nueva cita", description = "Registra una nueva cita médica asociando un paciente y médico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cita creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public Cita crearCita(@RequestBody Cita cita) {
        return citaService.crearCita(cita);
    }

    @Operation(summary = "Reporte de citas por Paciente", 
               description = "Consulta las citas locales e integra datos del paciente consumiendo el servicio externo mediante JWT.")
    @GetMapping("/reporte/paciente/{pacienteId}")
    public ResponseEntity<?> citasPorPaciente(
            @Parameter(description = "ID del paciente a consultar", example = "1") @PathVariable Long pacienteId,
            @Parameter(description = "Token de autorización Bearer") @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        List<Cita> citas = citaService.obtenerPorPaciente(pacienteId);
        if (authorization != null) {
            String token = authorization.replace("Bearer ", "");
            PacienteDTO paciente = pacienteClientService.obtenerPaciente(pacienteId, token);
            return ResponseEntity.ok(new ReportePaciente(paciente, citas));
        }
        return ResponseEntity.ok(citas);
    }

    @Operation(summary = "Reporte de citas por fecha", description = "Filtra las citas agendadas para un día específico.")
    @GetMapping("/reporte/fecha/{fecha}")
    public List<Cita> citasPorFecha(
            @Parameter(description = "Fecha en formato YYYY-MM-DD", example = "2026-06-25")
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        return citaService.obtenerPorFecha(fecha);
    }

    @Operation(summary = "Reporte de citas por Médico", description = "Filtra las citas correspondientes al nombre de un médico.")
    @GetMapping("/reporte/medico/{medicoNombre}")
    public List<Cita> citasPorMedico(
            @Parameter(description = "Nombre del médico", example = "Dr. House") @PathVariable String medicoNombre) {
        return citaService.obtenerPorMedico(medicoNombre);
    }

    record ReportePaciente(PacienteDTO paciente, List<Cita> citas) {}
}