package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.entidades.Notificacion;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityNotFoundException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NotificacionRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;


    @Override
    public Optional<Notificacion> findById(Long id) {
        Optional<Notificacion> notificacion = notificacionRepository.findById(id);
        if(!notificacion.isPresent()){
            throw new EntityNotFoundException("Notificacion", id);
        }
        return notificacion;
    }

    @Override
    public List<Notificacion> obtenerTodas() {
        List<Notificacion> notificaciones = notificacionRepository.findAll();
        if(notificaciones.isEmpty()){
            throw new EntityNotFoundException("Notificacion", "Ninguna notificacion encontrada");

        }
        return  notificaciones;
    }

    @Override
    public Notificacion crearNotificacion(Notificacion notificacion) {
        notificacion.setLeido(false);
        return notificacionRepository.save(notificacion);
    }

    @Override
    public void eliminarNotificacion(Long id) {
        Optional<Notificacion> existingNotificacion = notificacionRepository.findById(id);

        notificacionRepository.deleteById(id);
    }


}
