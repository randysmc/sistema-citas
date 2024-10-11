package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;

import java.util.Optional;

public interface RolService {
    Optional<Rol> findById(Long id);
}
