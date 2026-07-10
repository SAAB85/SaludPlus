package com.saludplus.atenciones.service;
import com.saludplus.atenciones.model.Atencion;
import com.saludplus.atenciones.repository.AtencionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
@Service
public class AtencionServiceImpl implements AtencionService {
    private static final Logger log = LoggerFactory.getLogger(AtencionServiceImpl.class);
    private final AtencionRepository atencionRepository;
    @Autowired
    public AtencionServiceImpl(AtencionRepository atencionRepository) {
        this.atencionRepository = atencionRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Atencion> obtenerTodos() {
        log.info("Consultando todas las atenciones");
        return atencionRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Atencion> buscarPorId(Long id) {
        log.info("Buscando atencion con ID: {}", id);
        return atencionRepository.findById(id);
    }
    @Override
    @Transactional
    public Atencion guardar(Atencion atencion) {
        log.info("Guardando nueva atencion para paciente ID: {}", atencion.getPacienteId());
        Atencion guardada = atencionRepository.save(atencion);
        log.info("Atencion guardada con ID: {}", guardada.getId());
        return guardada;
    }
    @Override
    @Transactional
    public Optional<Atencion> actualizar(Long id, Atencion atencionDetalles) {
        log.info("Actualizando atencion con ID: {}", id);
        return atencionRepository.findById(id).map(existente -> {
            existente.setPacienteId(atencionDetalles.getPacienteId());
            existente.setMedicoId(atencionDetalles.getMedicoId());
            existente.setFechaAtencion(atencionDetalles.getFechaAtencion());
            existente.setDiagnostico(atencionDetalles.getDiagnostico());
            existente.setTratamiento(atencionDetalles.getTratamiento());
            existente.setCosto(atencionDetalles.getCosto());
            log.info("Atencion actualizada correctamente");
            return atencionRepository.save(existente);
        });
    }
    @Override
    @Transactional
    public boolean eliminar(Long id) {
        log.info("Eliminando atencion con ID: {}", id);
        return atencionRepository.findById(id).map(existente -> {
            atencionRepository.delete(existente);
            log.info("Atencion eliminada correctamente");
            return true;
        }).orElse(false);
    }
}
