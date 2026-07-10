package com.saludplus.pacientes.service;

import com.saludplus.pacientes.model.Paciente;
import com.saludplus.pacientes.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    @Autowired
    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> obtenerTodos() {
        return pacienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    @Override
    @Transactional
    public Paciente guardar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    @Transactional
    public Optional<Paciente> actualizar(Long id, Paciente pacienteDetalles) {
        return pacienteRepository.findById(id).map(existente -> {
                    existente.setRut(pacienteDetalles.getRut());
                    existente.setNombre(pacienteDetalles.getNombre());
                    existente.setApellido(pacienteDetalles.getApellido());
                    existente.setEmail(pacienteDetalles.getEmail());
                    existente.setTelefono(pacienteDetalles.getTelefono());
                    existente.setFechaNacimiento(pacienteDetalles.getFechaNacimiento());
                    existente.setObraSocial(pacienteDetalles.getObraSocial());
            return pacienteRepository.save(existente);
        });
    }

    @Override
    @Transactional
    public boolean eliminar(Long id) {
        return pacienteRepository.findById(id).map(existente -> {
            pacienteRepository.delete(existente);
            return true;
        }).orElse(false);
    }
}
