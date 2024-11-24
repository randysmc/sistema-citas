package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Notificacion;

import java.util.List;
import java.util.Optional;

public interface NotificacionService {

    public Optional<Notificacion> findById(Long id);

    public List<Notificacion> obtenerTodas();

    public Notificacion crearNotificacion(Notificacion notificacion);

    public void eliminarNotificacion(Long id);


}
