package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.DTO.RolDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    //Optional<Rol> findById(Long id);
    Optional<RolDTO> findById(Long id);

    List<RolDTO> findAll();

    RolDTO save(RolDTO rolDTO);

    RolDTO update(RolDTO rolDTO);

    void delete(Long id);

    // Método para convertir de DTO a entidad si necesitas usarlo externamente
    Rol convertRolToEntity(RolDTO rolDTO);

    // Método para convertir de entidad a DTO
    RolDTO convertRolToDTO(Rol rol);
}
