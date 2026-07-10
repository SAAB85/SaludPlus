-- Script de inicialización para microservicio de FichaPacientes
CREATE TABLE IF NOT EXISTS fichapacientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pacienteid BIGINT NOT NULL,
    historialmedico TEXT,
    alergias VARCHAR(255),
    gruposanguineo VARCHAR(255),
    fechacreacion DATETIME NOT NULL,
    ultimaactualizacion DATETIME NOT NULL
);

-- Limpieza de tabla previa para asegurar consistencia
TRUNCATE TABLE fichapacientes;

-- Insertar registros semilla de prueba clínica
INSERT INTO fichapacientes (id, pacienteid, historialmedico, alergias, gruposanguineo, fechacreacion, ultimaactualizacion) VALUES (1, 1, 'Asma infantil, cirugía de apéndice en 2018. Hipertenso en tratamiento.', 'Penicilina, polen de plátano oriental', 'O-Positivo', '2024-01-10 09:00:00', '2026-07-09 14:00:00');
INSERT INTO fichapacientes (id, pacienteid, historialmedico, alergias, gruposanguineo, fechacreacion, ultimaactualizacion) VALUES (2, 2, 'Alergias estacionales, rinitis.', 'Ninguna', 'A-Positivo', '2026-07-09 15:45:00', '2026-07-09 15:45:00');
