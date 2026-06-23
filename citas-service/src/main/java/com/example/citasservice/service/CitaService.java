package com.example.citasservice.service;

import com.example.citasservice.model.Cita;
import com.example.citasservice.repository.CitaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> obtenerTodas() {
        return citaRepository.findAll();
    }

    public Optional<Cita> obtenerPorId(Long id) {
        return citaRepository.findById(id);
    }

    public List<Cita> obtenerPorPaciente(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId);
    }

    public List<Cita> obtenerPorFecha(LocalDate fecha) {
        return citaRepository.findByFecha(fecha);
    }

    public List<Cita> obtenerPorMedico(String medicoNombre) {
        return citaRepository.findByMedicoNombre(medicoNombre);
    }

    public Cita guardar(Cita cita) {
        return citaRepository.save(cita);
    }

    public Cita actualizar(Long id, Cita cita) {
        cita.setId(id);
        return citaRepository.save(cita);
    }

    public boolean eliminar(Long id) {
        if (citaRepository.existsById(id)) {
            citaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
