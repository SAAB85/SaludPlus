CREATE TABLE IF NOT EXISTS atencion (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_atencion DATE           NOT NULL,
    hora_atencion  TIME           NOT NULL,
    costo          DECIMAL(10,2)  NOT NULL DEFAULT 0.00,
    id_paciente    INT            NOT NULL,
    id_medico      INT            NOT NULL,
    comentario     VARCHAR(300),
    CONSTRAINT fk_atencion_paciente FOREIGN KEY (id_paciente) REFERENCES paciente(id),
    CONSTRAINT fk_atencion_medico   FOREIGN KEY (id_medico)   REFERENCES medico(id_medico)
);

