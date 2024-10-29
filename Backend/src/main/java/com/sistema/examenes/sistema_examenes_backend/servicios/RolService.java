package com.sistema.examenes.sistema_examenes_backend.servicios;

//import com.sistema.examenes.sistema_examenes_backend.DTO.RolDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    public Optional<Rol> findById(Long id);
    public List<Rol> findAll();
    public Rol save(Rol rol);
    public Rol update(Rol rol);
    public void delete(Long id);
}
