package com.ejemplo.saludplus.service;

import com.ejemplo.saludplus.model.Paciente;
import com.ejemplo.saludplus.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public List<Paciente> obtenerPacientes() {
        return repository.findAll();
    }

    public Paciente buscarPorId(int id) {
        Optional<Paciente> paciente = repository.findById(id);
        return paciente.orElse(null);
    }

    public Paciente guardar(Paciente paciente) {
        return repository.save(paciente);
    }

    public Paciente actualizar(int id, Paciente paciente) {
        paciente.setId(id);
        return repository.save(paciente);
    }

    public boolean eliminar(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}