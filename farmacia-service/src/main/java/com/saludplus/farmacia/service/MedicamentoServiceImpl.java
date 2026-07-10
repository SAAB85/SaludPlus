package com.saludplus.farmacia.service;

import com.saludplus.farmacia.model.Medicamento;
import com.saludplus.farmacia.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    @Autowired
    public MedicamentoServiceImpl(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> obtenerTodos() {
        return medicamentoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medicamento> buscarPorId(Long id) {
        return medicamentoRepository.findById(id);
    }

    @Override
    @Transactional
    public Medicamento guardar(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    @Override
    @Transactional
    public Optional<Medicamento> actualizar(Long id, Medicamento medicamentoDetalles) {
        return medicamentoRepository.findById(id).map(existente -> {
                    existente.setNombre(medicamentoDetalles.getNombre());
                    existente.setDescripcion(medicamentoDetalles.getDescripcion());
                    existente.setStock(medicamentoDetalles.getStock());
                    existente.setPrecio(medicamentoDetalles.getPrecio());
                    existente.setLaboratorio(medicamentoDetalles.getLaboratorio());
            return medicamentoRepository.save(existente);
        });
    }

    @Override
    @Transactional
    public boolean eliminar(Long id) {
        return medicamentoRepository.findById(id).map(existente -> {
            medicamentoRepository.delete(existente);
            return true;
        }).orElse(false);
    }
}
