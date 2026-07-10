package com.saludplus.medicos.service;

import com.saludplus.medicos.model.Medico;
import com.saludplus.medicos.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;

    @Autowired
    public MedicoServiceImpl(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> obtenerTodos() {
        return medicoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medico> buscarPorId(Long id) {
        return medicoRepository.findById(id);
    }

    @Override
    @Transactional
    public Medico guardar(Medico medico) {
        return medicoRepository.save(medico);
    }

    @Override
    @Transactional
    public Optional<Medico> actualizar(Long id, Medico medicoDetalles) {
        return medicoRepository.findById(id).map(existente -> {
                    existente.setRut(medicoDetalles.getRut());
                    existente.setNombre(medicoDetalles.getNombre());
                    existente.setApellido(medicoDetalles.getApellido());
                    existente.setEspecialidad(medicoDetalles.getEspecialidad());
                    existente.setEmail(medicoDetalles.getEmail());
                    existente.setTelefono(medicoDetalles.getTelefono());
                    existente.setLicenciaMedica(medicoDetalles.getLicenciaMedica());
            return medicoRepository.save(existente);
        });
    }

    @Override
    @Transactional
    public boolean eliminar(Long id) {
        return medicoRepository.findById(id).map(existente -> {
            medicoRepository.delete(existente);
            return true;
        }).orElse(false);
    }
}
