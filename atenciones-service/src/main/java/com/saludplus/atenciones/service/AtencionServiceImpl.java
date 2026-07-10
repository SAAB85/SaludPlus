package com.saludplus.atenciones.service;

import com.saludplus.atenciones.model.Atencion;
import com.saludplus.atenciones.repository.AtencionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AtencionServiceImpl implements AtencionService {

    private final AtencionRepository atencionRepository;

    @Autowired
    public AtencionServiceImpl(AtencionRepository atencionRepository) {
        this.atencionRepository = atencionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Atencion> obtenerTodos() {
        return atencionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Atencion> buscarPorId(Long id) {
        return atencionRepository.findById(id);
    }

    @Override
    @Transactional
    public Atencion guardar(Atencion atencion) {
        return atencionRepository.save(atencion);
    }

    @Override
    @Transactional
    public Optional<Atencion> actualizar(Long id, Atencion atencionDetalles) {
        return atencionRepository.findById(id).map(existente -> {
                    existente.setPacienteId(atencionDetalles.getPacienteId());
                    existente.setMedicoId(atencionDetalles.getMedicoId());
                    existente.setFechaAtencion(atencionDetalles.getFechaAtencion());
                    existente.setDiagnostico(atencionDetalles.getDiagnostico());
                    existente.setTratamiento(atencionDetalles.getTratamiento());
                    existente.setCosto(atencionDetalles.getCosto());
            return atencionRepository.save(existente);
        });
    }

    @Override
    @Transactional
    public boolean eliminar(Long id) {
        return atencionRepository.findById(id).map(existente -> {
            atencionRepository.delete(existente);
            return true;
        }).orElse(false);
    }
}
