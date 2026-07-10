package com.saludplus.pagos.service;
import com.saludplus.pagos.model.Pago;
import com.saludplus.pagos.repository.PagoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
@Service
public class PagoServiceImpl implements PagoService {
    private static final Logger log = LoggerFactory.getLogger(PagoServiceImpl.class);
    private final PagoRepository pagoRepository;
    @Autowired
    public PagoServiceImpl(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Pago> obtenerTodos() {
        log.info("Consultando todos los pagos");
        return pagoRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Pago> buscarPorId(Long id) {
        log.info("Buscando pago con ID: {}", id);
        return pagoRepository.findById(id);
    }
    @Override
    @Transactional
    public Pago guardar(Pago pago) {
        log.info("Guardando nuevo pago de monto: {}", pago.getMonto());
        Pago guardado = pagoRepository.save(pago);
        log.info("Pago guardado con ID: {}", guardado.getId());
        return guardado;
    }
    @Override
    @Transactional
    public Optional<Pago> actualizar(Long id, Pago pagoDetalles) {
        log.info("Actualizando pago con ID: {}", id);
        return pagoRepository.findById(id).map(existente -> {
            existente.setAtencionId(pagoDetalles.getAtencionId());
            existente.setMonto(pagoDetalles.getMonto());
            existente.setMetodoPago(pagoDetalles.getMetodoPago());
            existente.setEstado(pagoDetalles.getEstado());
            log.info("Pago actualizado correctamente");
            return pagoRepository.save(existente);
        });
    }
    @Override
    @Transactional
    public boolean eliminar(Long id) {
        log.info("Eliminando pago con ID: {}", id);
        return pagoRepository.findById(id).map(existente -> {
            pagoRepository.delete(existente);
            log.info("Pago eliminado correctamente");
            return true;
        }).orElse(false);
    }
}
