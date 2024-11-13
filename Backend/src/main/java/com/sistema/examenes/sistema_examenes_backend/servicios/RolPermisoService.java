package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Permiso;

import java.util.List;

public interface RolPermisoService {

    public void asignarPermisosARol(Long rolId, List<Long> permisoId);

    public List<Permiso> obtenerPermisosDeRol(Long rolId);
}
