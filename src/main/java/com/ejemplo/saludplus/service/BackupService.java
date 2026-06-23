package com.ejemplo.saludplus.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Servicio de backup automatizado para la BD del hospital.
 * Ejecuta mysqldump (incluido en Laragon) y guarda un archivo .sql.
 */
@Service
public class BackupService {

    private static final String DUMP_PATH =
            "C:/laragon/bin/mysql/mysql-8.0.30-winx64/bin/mysqldump";
    private static final String DB_NAME   = "db_hospital_vm";
    private static final String DB_USER   = "root";
    private static final String DB_PASS   = "";
    private static final String SAVE_DIR  = "C:/backups/";

    public String crearBackup() {
        try {
            new File(SAVE_DIR).mkdirs();

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String savePath = SAVE_DIR + "backup_hospital_" + timestamp + ".sql";

            String command = String.format("%s -u %s %s --databases %s -r %s",
                    DUMP_PATH,
                    DB_USER,
                    DB_PASS.isEmpty() ? "" : "-p" + DB_PASS,
                    DB_NAME,
                    savePath);

            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                return "Backup creado con éxito en: " + savePath;
            } else {
                return "Error al crear el backup (código: " + exitCode + ")";
            }
        } catch (Exception e) {
            return "Excepción durante el backup: " + e.getMessage();
        }
    }
}
