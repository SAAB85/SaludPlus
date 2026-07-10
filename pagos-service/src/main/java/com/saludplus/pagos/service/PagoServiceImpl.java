package com.saludplus.pagos.service;

import com.saludplus.pagos.model.Pago;
import com.saludplus.pagos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;

    @Autowired
    public PagoServiceImpl(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pago> obtenerTodos() {
        return pagoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pago> buscarPorId(Long id) {
        return pagoRepository.findById(id);
    }

    @Override
    @Transactional
    public Pago guardar(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    @Transactional
    public Optional<Pago> actualizar(Long id, Pago pagoDetalles) {
        return pagoRepository.findById(id).map(existente -> {
                    existente.setAtencionId(pagoDetalles.getAtencionId());
                    existente.setMonto(pagoDetalles.getMonto());
                    existente.setFechaPago(pagoDetalles.getFechaPago());
                    existente.setMetodoPago(pagoDetalles.getMetodoPago());
                    existente.setEstado(pagoDetalles.getEstado());
            return pagoRepository.save(existente);
        });
    }

    @Override
    @Transactional
    public boolean eliminar(Long id) {
        return pagoRepository.findById(id).map(existente -> {
            pagoRepository.delete(existente);
            return true;
        }).orElse(false);
    }
}
