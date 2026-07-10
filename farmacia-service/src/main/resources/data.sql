-- Script de inicialización para microservicio de Medicamentos
CREATE TABLE IF NOT EXISTS medicamentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    descripcion TEXT,
    stock INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    laboratorio VARCHAR(255)
);

-- Limpieza de tabla previa para asegurar consistencia
TRUNCATE TABLE medicamentos;

-- Insertar registros semilla de prueba clínica
INSERT INTO medicamentos (id, nombre, descripcion, stock, precio, laboratorio) VALUES (1, 'Paracetamol 500mg', 'Analgésico y antipirético para el alivio del dolor y la fiebre.', 250, 1200.00, 'Chile Laboratorios S.A.');
INSERT INTO medicamentos (id, nombre, descripcion, stock, precio, laboratorio) VALUES (2, 'Maria', 'Ibuprofeno 400mg comprimidos antiinflamatorios', 120, 45000.00, 'Laboratorio Saval S.A.');
