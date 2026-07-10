-- Script de inicialización para microservicio de Pacientes
CREATE TABLE IF NOT EXISTS pacientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(255),
    nombre VARCHAR(255),
    apellido VARCHAR(255),
    email VARCHAR(255),
    telefono VARCHAR(255),
    fechanacimiento DATE NOT NULL,
    obrasocial VARCHAR(255)
);

-- Limpieza de tabla previa para asegurar consistencia
TRUNCATE TABLE pacientes;

-- Insertar registros semilla de prueba clínica
INSERT INTO pacientes (id, rut, nombre, apellido, email, telefono, fechanacimiento, obrasocial) VALUES (1, '12345678-9', 'Juan', 'Pérez', 'juan.perez@saludplus.cl', '+56912345678', '1990-05-15', 'Fonasa');
INSERT INTO pacientes (id, rut, nombre, apellido, email, telefono, fechanacimiento, obrasocial) VALUES (2, '15432654-K', 'Maria', 'López', 'maria.lopez@saludplus.cl', '+56998877665', '1985-09-22', 'Isapre CruzBlanca');
