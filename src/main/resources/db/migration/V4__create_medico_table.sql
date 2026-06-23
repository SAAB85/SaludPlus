CREATE TABLE IF NOT EXISTS medico (
    id_medico       INT AUTO_INCREMENT PRIMARY KEY,
    run_medico      VARCHAR(10)  NOT NULL UNIQUE,
    nombre_completo VARCHAR(100) NOT NULL,
    especialidad    VARCHAR(100) NOT NULL,
    jefe_turno      CHAR(1)      NOT NULL DEFAULT 'N'
);

