-- Script de inicialización para microservicio de Medicos
CREATE TABLE IF NOT EXISTS medicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(255),
    nombre VARCHAR(255),
    apellido VARCHAR(255),
    especialidad VARCHAR(255),
    email VARCHAR(255),
    telefono VARCHAR(255),
    licenciamedica VARCHAR(255)
);

-- Limpieza de tabla previa para asegurar consistencia
TRUNCATE TABLE medicos;

-- Insertar registros semilla de prueba clínica
INSERT INTO medicos (id, rut, nombre, apellido, especialidad, email, telefono, licenciamedica) VALUES (1, '9876543-2', 'Carlos', 'Soto', 'Cardiología', 'carlos.soto@saludplus.cl', '+56987654321', 'LIC-4523-SP');
INSERT INTO medicos (id, rut, nombre, apellido, especialidad, email, telefono, licenciamedica) VALUES (2, '15432654-K', 'Maria', 'López', 'Pediatría', 'maria.lopez@saludplus.cl', '+56998877665', 'LIC-8912-SP');
