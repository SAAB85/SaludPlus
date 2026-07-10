package com.saludplus.farmacia.service;
import com.saludplus.farmacia.model.Medicamento;
import com.saludplus.farmacia.repository.MedicamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
@Service
public class MedicamentoServiceImpl implements MedicamentoService {
    private static final Logger log = LoggerFactory.getLogger(MedicamentoServiceImpl.class);
    private final MedicamentoRepository medicamentoRepository;
    @Autowired
    public MedicamentoServiceImpl(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> obtenerTodos() {
        log.info("Consultando todos los medicamentos");
        return medicamentoRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Medicamento> buscarPorId(Long id) {
        log.info("Buscando medicamento con ID: {}", id);
        return medicamentoRepository.findById(id);
    }
    @Override
    @Transactional
    public Medicamento guardar(Medicamento medicamento) {
        log.info("Guardando nuevo medicamento: {}", medicamento.getNombre());
        Medicamento guardado = medicamentoRepository.save(medicamento);
        log.info("Medicamento guardado con ID: {}", guardado.getId());
        return guardado;
    }
    @Override
    @Transactional
    public Optional<Medicamento> actualizar(Long id, Medicamento medicamentoDetalles) {
        log.info("Actualizando medicamento con ID: {}", id);
        return medicamentoRepository.findById(id).map(existente -> {
            existente.setNombre(medicamentoDetalles.getNombre());
            existente.setDescripcion(medicamentoDetalles.getDescripcion());
            existente.setStock(medicamentoDetalles.getStock());
            existente.setPrecio(medicamentoDetalles.getPrecio());
            existente.setLaboratorio(medicamentoDetalles.getLaboratorio());
            log.info("Medicamento actualizado correctamente");
            return medicamentoRepository.save(existente);
        });
    }
    @Override
    @Transactional
    public boolean eliminar(Long id) {
        log.info("Eliminando medicamento con ID: {}", id);
        return medicamentoRepository.findById(id).map(existente -> {
            medicamentoRepository.delete(existente);
            log.info("Medicamento eliminado correctamente");
            return true;
        }).orElse(false);
    }
}
