package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioNotificacionRepository extends JpaRepository<UsuarioNotificacion, Long> {

    public List<UsuarioNotificacion> findByUsuarioId(Long usuarioId);

    public List<UsuarioNotificacion> findByNotificacionId(Long notificacionId);
}
