package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.entidades.Notificacion;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioNotificacion;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityNotFoundException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NotificacionRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioNotificacionRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.UsuarioNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioNotificacionServiceImpl implements UsuarioNotificacionService {

    @Autowired
    private UsuarioNotificacionRepository usuarioNotificacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Override
    public UsuarioNotificacion asignarNotificacionAUsuario(UsuarioNotificacion usuarioNotificacion) {
        Long usuarioId = usuarioNotificacion.getUsuario().getId();
        Long notificacionId = usuarioNotificacion.getNotificacion().getId();

        // Verificamos que el usuario y la notificación existan
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario", usuarioId));
        Notificacion notificacion = notificacionRepository.findById(notificacionId)
                .orElseThrow(() -> new EntityNotFoundException("Notificacion", notificacionId));

        // Asignamos la notificación al usuario
        usuarioNotificacion.setUsuario(usuario);
        usuarioNotificacion.setNotificacion(notificacion);

        // Guardamos la relación en la base de datos
        return usuarioNotificacionRepository.save(usuarioNotificacion);
    }

    @Override
    public List<UsuarioNotificacion> obtenerNotificacionesPorUsuario(Long usuarioId) {
        return usuarioNotificacionRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<UsuarioNotificacion> obtenerUsuariosPorNotificacion(Long notificacionId) {
        return usuarioNotificacionRepository.findByNotificacionId(notificacionId);
    }

    @Override
    public void eliminarUsuarioNotificacion(Long id) {
        usuarioNotificacionRepository.deleteById(id);
    }
}
