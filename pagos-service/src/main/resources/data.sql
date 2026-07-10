-- Script de inicialización para microservicio de Pagos
CREATE TABLE IF NOT EXISTS pagos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    atencionid BIGINT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    fechapago DATETIME NOT NULL,
    metodopago VARCHAR(255),
    estado VARCHAR(255)
);

-- Limpieza de tabla previa para asegurar consistencia
TRUNCATE TABLE pagos;

-- Insertar registros semilla de prueba clínica
INSERT INTO pagos (id, atencionid, monto, fechapago, metodopago, estado) VALUES (1, 1, 35000.00, '2026-07-09 11:00:00', 'Tarjeta de Crédito', 'PAGADO');
INSERT INTO pagos (id, atencionid, monto, fechapago, metodopago, estado) VALUES (2, 2, 45000.00, '2026-07-09 15:45:00', 'Efectivo', 'PAGADO');
