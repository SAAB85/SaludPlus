package com.ejemplo.saludplus.service;

import com.ejemplo.saludplus.dto.PacienteDTO;
import com.ejemplo.saludplus.model.Paciente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargaMasivaPacienteService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void procesarCarga(List<PacienteDTO> lista) {

        int batchSize = 50;

        for (int i = 0; i < lista.size(); i++) {

            PacienteDTO dto = lista.get(i);

            Paciente paciente = new Paciente();
            paciente.setRut(dto.getRut());
            paciente.setNombre(dto.getNombre());
            paciente.setEdad(dto.getEdad());
            paciente.setTelefono(dto.getTelefono());

            entityManager.persist(paciente);

            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }
}