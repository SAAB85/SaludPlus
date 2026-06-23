CREATE TABLE IF NOT EXISTS tipo_usuario (
    id     INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

-- Datos iniciales
INSERT IGNORE INTO tipo_usuario (nombre) VALUES ('FONASA'), ('ISAPRE'), ('PARTICULAR');
