package com.saludplus.atenciones.controller;

import com.saludplus.atenciones.model.Atencion;
import com.saludplus.atenciones.service.AtencionService;
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
@RequestMapping("/api/atenciones")
@Tag(name = "Atencion", description = "Controlador para gestión de Atencions")
public class AtencionController {

    private final AtencionService atencionService;

    @Autowired
    public AtencionController(AtencionService atencionService) {
        this.atencionService = atencionService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los Atencions", description = "Retorna una lista completa de todos los registros del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    public ResponseEntity<List<Atencion>> obtenerTodos() {
        return ResponseEntity.ok(atencionService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Atencion por ID", description = "Retorna un solo registro a partir de su identificador numérico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<Atencion> buscarPorId(@PathVariable Long id) {
        return atencionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo Atencion", description = "Guarda un nuevo registro en el sistema clínico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<Atencion> guardar(@RequestBody Atencion atencion) {
        Atencion guardado = atencionService.guardar(atencion);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Atencion por ID", description = "Modifica los atributos de un registro existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<Atencion> actualizar(@PathVariable Long id, @RequestBody Atencion atencion) {
        return atencionService.actualizar(id, atencion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Atencion por ID", description = "Elimina de forma permanente un registro del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (atencionService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
