CREATE TABLE IF NOT EXISTS cita (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    paciente_id BIGINT       NOT NULL,
    medico_nombre VARCHAR(100) NOT NULL,
    fecha       DATE         NOT NULL,
    hora        TIME         NOT NULL,
    motivo      VARCHAR(255) NOT NULL,
    estado      VARCHAR(20)  NOT NULL DEFAULT 'PENDIENTE',
    comentarios TEXT,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
