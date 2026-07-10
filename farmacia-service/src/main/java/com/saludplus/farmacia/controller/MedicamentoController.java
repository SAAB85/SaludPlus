package com.saludplus.farmacia.controller;

import com.saludplus.farmacia.model.Medicamento;
import com.saludplus.farmacia.service.MedicamentoService;
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
@RequestMapping("/api/farmacia")
@Tag(name = "Medicamento", description = "Controlador para gestión de Medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    @Autowired
    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los Medicamentos", description = "Retorna una lista completa de todos los registros del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    public ResponseEntity<List<Medicamento>> obtenerTodos() {
        return ResponseEntity.ok(medicamentoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Medicamento por ID", description = "Retorna un solo registro a partir de su identificador numérico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<Medicamento> buscarPorId(@PathVariable Long id) {
        return medicamentoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo Medicamento", description = "Guarda un nuevo registro en el sistema clínico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<Medicamento> guardar(@RequestBody Medicamento medicamento) {
        Medicamento guardado = medicamentoService.guardar(medicamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Medicamento por ID", description = "Modifica los atributos de un registro existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<Medicamento> actualizar(@PathVariable Long id, @RequestBody Medicamento medicamento) {
        return medicamentoService.actualizar(id, medicamento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Medicamento por ID", description = "Elimina de forma permanente un registro del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (medicamentoService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
