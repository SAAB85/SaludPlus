package com.saludplus.fichas.service;

import com.saludplus.fichas.model.FichaPaciente;
import com.saludplus.fichas.repository.FichaPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FichaPacienteServiceImpl implements FichaPacienteService {

    private final FichaPacienteRepository fichaPacienteRepository;

    @Autowired
    public FichaPacienteServiceImpl(FichaPacienteRepository fichaPacienteRepository) {
        this.fichaPacienteRepository = fichaPacienteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FichaPaciente> obtenerTodos() {
        return fichaPacienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FichaPaciente> buscarPorId(Long id) {
        return fichaPacienteRepository.findById(id);
    }

    @Override
    @Transactional
    public FichaPaciente guardar(FichaPaciente fichaPaciente) {
        return fichaPacienteRepository.save(fichaPaciente);
    }

    @Override
    @Transactional
    public Optional<FichaPaciente> actualizar(Long id, FichaPaciente fichaPacienteDetalles) {
        return fichaPacienteRepository.findById(id).map(existente -> {
                    existente.setPacienteId(fichaPacienteDetalles.getPacienteId());
                    existente.setHistorialMedico(fichaPacienteDetalles.getHistorialMedico());
                    existente.setAlergias(fichaPacienteDetalles.getAlergias());
                    existente.setGrupoSanguineo(fichaPacienteDetalles.getGrupoSanguineo());
                    existente.setFechaCreacion(fichaPacienteDetalles.getFechaCreacion());
                    existente.setUltimaActualizacion(fichaPacienteDetalles.getUltimaActualizacion());
            return fichaPacienteRepository.save(existente);
        });
    }

    @Override
    @Transactional
    public boolean eliminar(Long id) {
        return fichaPacienteRepository.findById(id).map(existente -> {
            fichaPacienteRepository.delete(existente);
            return true;
        }).orElse(false);
    }
}
