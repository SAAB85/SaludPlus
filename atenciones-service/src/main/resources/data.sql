-- Script de inicialización para microservicio de Atencions
CREATE TABLE IF NOT EXISTS atencions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pacienteid BIGINT NOT NULL,
    medicoid BIGINT NOT NULL,
    fechaatencion DATETIME NOT NULL,
    diagnostico TEXT,
    tratamiento TEXT,
    costo DECIMAL(10,2) NOT NULL
);

-- Limpieza de tabla previa para asegurar consistencia
TRUNCATE TABLE atencions;

-- Insertar registros semilla de prueba clínica
INSERT INTO atencions (id, pacienteid, medicoid, fechaatencion, diagnostico, tratamiento, costo) VALUES (1, 1, 2, '2026-07-09 10:30:00', 'Hipertensión arterial moderada', 'Enalapril 10mg cada 12 horas por 1 mes y control', 35000.00);
INSERT INTO atencions (id, pacienteid, medicoid, fechaatencion, diagnostico, tratamiento, costo) VALUES (2, 2, 2, '2026-07-09 15:45:00', 'Gripe estacional severa', 'Paracetamol 500mg cada 8 horas por 3 días y reposo', 45000.00);
