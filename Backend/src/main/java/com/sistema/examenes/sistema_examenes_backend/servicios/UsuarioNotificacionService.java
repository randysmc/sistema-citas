package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioNotificacion;

import java.util.List;

public interface UsuarioNotificacionService {

    UsuarioNotificacion asignarNotificacionAUsuario(UsuarioNotificacion usuarioNotificacion);

    List<UsuarioNotificacion> obtenerNotificacionesPorUsuario(Long usuarioId);

    List<UsuarioNotificacion> obtenerUsuariosPorNotificacion(Long notificacionId);

    void eliminarUsuarioNotificacion(Long id);
}
