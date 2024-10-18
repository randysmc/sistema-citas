package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.DTO.RolDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    Optional<Rol> findById(Long id);
    List<Rol> findAll();
    Rol save(Rol rol);
    Rol update(Rol rol);
    void delete(Long id);
}
