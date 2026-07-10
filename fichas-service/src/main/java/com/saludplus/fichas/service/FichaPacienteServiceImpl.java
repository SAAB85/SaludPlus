package com.saludplus.fichas.service;
import com.saludplus.fichas.model.FichaPaciente;
import com.saludplus.fichas.repository.FichaPacienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
@Service
public class FichaPacienteServiceImpl implements FichaPacienteService {
    private static final Logger log = LoggerFactory.getLogger(FichaPacienteServiceImpl.class);
    private final FichaPacienteRepository fichaRepository;
    @Autowired
    public FichaPacienteServiceImpl(FichaPacienteRepository fichaRepository) {
        this.fichaRepository = fichaRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public List<FichaPaciente> obtenerTodos() {
        log.info("Consultando todas las fichas de pacientes");
        return fichaRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<FichaPaciente> buscarPorId(Long id) {
        log.info("Buscando ficha con ID: {}", id);
        return fichaRepository.findById(id);
    }
    @Override
    @Transactional
    public FichaPaciente guardar(FichaPaciente ficha) {
        log.info("Guardando nueva ficha para paciente ID: {}", ficha.getPacienteId());
        FichaPaciente guardada = fichaRepository.save(ficha);
        log.info("Ficha guardada con ID: {}", guardada.getId());
        return guardada;
    }
    @Override
    @Transactional
    public Optional<FichaPaciente> actualizar(Long id, FichaPaciente fichaDetalles) {
        log.info("Actualizando ficha con ID: {}", id);
        return fichaRepository.findById(id).map(existente -> {
            existente.setPacienteId(fichaDetalles.getPacienteId());
            existente.setGrupoSanguineo(fichaDetalles.getGrupoSanguineo());
            existente.setAlergias(fichaDetalles.getAlergias());
            log.info("Ficha actualizada correctamente");
            return fichaRepository.save(existente);
        });
    }
    @Override
    @Transactional
    public boolean eliminar(Long id) {
        log.info("Eliminando ficha con ID: {}", id);
        return fichaRepository.findById(id).map(existente -> {
            fichaRepository.delete(existente);
            log.info("Ficha eliminada correctamente");
            return true;
        }).orElse(false);
    }
}
