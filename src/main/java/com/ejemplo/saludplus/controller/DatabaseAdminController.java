package com.ejemplo.saludplus.controller;

import com.ejemplo.saludplus.service.BackupService;
import org.flywaydb.core.Flyway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de administración de BD.
 * Expone endpoints para reparar Flyway y generar backups manuales.
 * ⚠️ En producción, proteger estos endpoints con rol ADMIN.
 */
@RestController
@RequestMapping("/admin/db")
public class DatabaseAdminController {

    private final Flyway flyway;
    private final BackupService backupService;

    public DatabaseAdminController(Flyway flyway, BackupService backupService) {
        this.flyway = flyway;
        this.backupService = backupService;
    }

    /**
     * Limpia entradas fallidas en flyway_schema_history.
     * Usar cuando la app no levanta por una migración con error.
     */
    @PostMapping("/repair")
    public ResponseEntity<String> repararFlyway() {
        flyway.repair();
        return ResponseEntity.ok("Historial de Flyway reparado. Ya puedes reintentar la migración.");
    }

    /**
     * Genera un backup manual de la base de datos.
     */
    @PostMapping("/backup")
    public ResponseEntity<String> generarBackup() {
        String resultado = backupService.crearBackup();
        return ResponseEntity.ok(resultado);
    }
}
