package com.ejemplo.saludplus.controller;

import com.ejemplo.saludplus.model.Paciente;
import com.ejemplo.saludplus.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@CrossOrigin(origins = "*")
@Tag(name = "Pacientes", description = "Gestión de pacientes del Hospital Vida & Meditación. "
        + "Permite registrar, consultar, actualizar y eliminar pacientes del sistema.")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos los pacientes",
            description = "Retorna la lista completa de pacientes registrados, incluyendo su tipo de usuario, atenciones y ficha clínica.")
    @GetMapping
    public List<Paciente> obtenerPacientes() {
        return service.obtenerPacientes();
    }

    @Operation(summary = "Buscar paciente por ID",
            description = "Busca y retorna un paciente específico por su identificador único.")
    @GetMapping("/{id}")
    public Paciente buscarPorId(
            @Parameter(description = "ID único del paciente") @PathVariable int id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Registrar nuevo paciente",
            description = "Crea un nuevo registro de paciente en el sistema con sus datos personales.")
    @PostMapping
    public Paciente guardar(@RequestBody Paciente paciente) {
        return service.guardar(paciente);
    }

    @Operation(summary = "Actualizar paciente",
            description = "Actualiza los datos de un paciente existente identificado por su ID.")
    @PutMapping("/{id}")
    public Paciente actualizar(
            @Parameter(description = "ID del paciente a actualizar") @PathVariable int id,
            @RequestBody Paciente paciente) {
        return service.actualizar(id, paciente);
    }

    @Operation(summary = "Eliminar paciente",
            description = "Elimina un paciente del sistema por su ID.")
    @DeleteMapping("/{id}")
    public boolean eliminar(
            @Parameter(description = "ID del paciente a eliminar") @PathVariable int id) {
        return service.eliminar(id);
    }
}