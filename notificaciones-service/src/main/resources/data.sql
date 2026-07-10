-- Script de inicialización para microservicio de Notificacions
CREATE TABLE IF NOT EXISTS notificacions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pacienteid BIGINT NOT NULL,
    mensaje TEXT,
    fechaenvio DATETIME NOT NULL,
    canal VARCHAR(255),
    leido BOOLEAN NOT NULL DEFAULT FALSE
);

-- Limpieza de tabla previa para asegurar consistencia
TRUNCATE TABLE notificacions;

-- Insertar registros semilla de prueba clínica
INSERT INTO notificacions (id, pacienteid, mensaje, fechaenvio, canal, leido) VALUES (1, 1, 'Estimado Juan, le recordamos su cita el día de mañana a las 10:30 AM.', '2026-07-08 18:00:00', 'SMS', false);
INSERT INTO notificacions (id, pacienteid, mensaje, fechaenvio, canal, leido) VALUES (2, 2, 'Estimada Maria, su receta de Ibuprofeno está lista para retiro en farmacia.', '2026-07-09 15:45:00', 'EMAIL', true);
