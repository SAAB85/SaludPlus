CREATE TABLE IF NOT EXISTS ficha_paciente (
    id_paciente      INT          NOT NULL PRIMARY KEY,
    datos_personales VARCHAR(100),
    datos_personales_2 VARCHAR(100),
    datos_personales_3 VARCHAR(100),
    datos_personales_4 VARCHAR(100),
    datos_personales_5 VARCHAR(100),
    CONSTRAINT fk_ficha_paciente FOREIGN KEY (id_paciente) REFERENCES paciente(id)
);

