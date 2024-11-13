package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Permiso;


import java.util.List;
import java.util.Optional;

public interface PermisoService {

    public Optional<Permiso> findById(Long id);

    public List<Permiso> findAll();

    public Permiso save(Permiso rol);

    public Permiso update(Permiso rol);

    public void delete(Long id);
}
