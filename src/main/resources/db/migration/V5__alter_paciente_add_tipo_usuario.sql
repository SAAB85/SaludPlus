ALTER TABLE paciente
    ADD COLUMN tipo_usuario_id INT,
    ADD CONSTRAINT fk_paciente_tipo_usuario
        FOREIGN KEY (tipo_usuario_id) REFERENCES tipo_usuario(id);

