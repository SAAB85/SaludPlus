package com.saludplus.notificaciones.service;

import com.saludplus.notificaciones.model.Notificacion;
import com.saludplus.notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;

    @Autowired
    public NotificacionServiceImpl(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> obtenerTodos() {
        return notificacionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Notificacion> buscarPorId(Long id) {
        return notificacionRepository.findById(id);
    }

    @Override
    @Transactional
    public Notificacion guardar(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    @Override
    @Transactional
    public Optional<Notificacion> actualizar(Long id, Notificacion notificacionDetalles) {
        return notificacionRepository.findById(id).map(existente -> {
                    existente.setPacienteId(notificacionDetalles.getPacienteId());
                    existente.setMensaje(notificacionDetalles.getMensaje());
                    existente.setFechaEnvio(notificacionDetalles.getFechaEnvio());
                    existente.setCanal(notificacionDetalles.getCanal());
                    existente.setLeido(notificacionDetalles.getLeido());
            return notificacionRepository.save(existente);
        });
    }

    @Override
    @Transactional
    public boolean eliminar(Long id) {
        return notificacionRepository.findById(id).map(existente -> {
            notificacionRepository.delete(existente);
            return true;
        }).orElse(false);
    }
}
