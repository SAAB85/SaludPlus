package com.saludplus.notificaciones.service;
import com.saludplus.notificaciones.model.Notificacion;
import com.saludplus.notificaciones.repository.NotificacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
@Service
public class NotificacionServiceImpl implements NotificacionService {
    private static final Logger log = LoggerFactory.getLogger(NotificacionServiceImpl.class);
    private final NotificacionRepository notificacionRepository;
    @Autowired
    public NotificacionServiceImpl(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> obtenerTodos() {
        log.info("Consultando todas las notificaciones");
        return notificacionRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Notificacion> buscarPorId(Long id) {
        log.info("Buscando notificacion con ID: {}", id);
        return notificacionRepository.findById(id);
    }
    @Override
    @Transactional
    public Notificacion guardar(Notificacion notificacion) {
        log.info("Guardando nueva notificacion para paciente ID: {}", notificacion.getPacienteId());
        Notificacion guardada = notificacionRepository.save(notificacion);
        log.info("Notificacion guardada con ID: {}", guardada.getId());
        return guardada;
    }
    @Override
    @Transactional
    public Optional<Notificacion> actualizar(Long id, Notificacion notificacionDetalles) {
        log.info("Actualizando notificacion con ID: {}", id);
        return notificacionRepository.findById(id).map(existente -> {
            existente.setPacienteId(notificacionDetalles.getPacienteId());
            existente.setMensaje(notificacionDetalles.getMensaje());
            existente.setCanal(notificacionDetalles.getCanal());
            existente.setLeido(notificacionDetalles.getLeido());
            log.info("Notificacion actualizada correctamente");
            return notificacionRepository.save(existente);
        });
    }
    @Override
    @Transactional
    public boolean eliminar(Long id) {
        log.info("Eliminando notificacion con ID: {}", id);
        return notificacionRepository.findById(id).map(existente -> {
            notificacionRepository.delete(existente);
            log.info("Notificacion eliminada correctamente");
            return true;
        }).orElse(false);
    }
}
