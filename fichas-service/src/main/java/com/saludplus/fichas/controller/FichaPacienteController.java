package com.saludplus.fichas.controller;

import com.saludplus.fichas.model.FichaPaciente;
import com.saludplus.fichas.service.FichaPacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fichas")
@Tag(name = "FichaPaciente", description = "Controlador para gestión de FichaPacientes")
public class FichaPacienteController {

    private final FichaPacienteService fichaPacienteService;

    @Autowired
    public FichaPacienteController(FichaPacienteService fichaPacienteService) {
        this.fichaPacienteService = fichaPacienteService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los FichaPacientes", description = "Retorna una lista completa de todos los registros del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    public ResponseEntity<List<FichaPaciente>> obtenerTodos() {
        return ResponseEntity.ok(fichaPacienteService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar FichaPaciente por ID", description = "Retorna un solo registro a partir de su identificador numérico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<FichaPaciente> buscarPorId(@PathVariable Long id) {
        return fichaPacienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo FichaPaciente", description = "Guarda un nuevo registro en el sistema clínico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<FichaPaciente> guardar(@RequestBody FichaPaciente fichaPaciente) {
        FichaPaciente guardado = fichaPacienteService.guardar(fichaPaciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar FichaPaciente por ID", description = "Modifica los atributos de un registro existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<FichaPaciente> actualizar(@PathVariable Long id, @RequestBody FichaPaciente fichaPaciente) {
        return fichaPacienteService.actualizar(id, fichaPaciente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar FichaPaciente por ID", description = "Elimina de forma permanente un registro del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (fichaPacienteService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
