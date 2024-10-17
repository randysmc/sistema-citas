package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.DTO.RolDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolServiceImplementacion implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Optional<RolDTO> findById(Long id) {
        return rolRepository.findById(id).map(this::convertRolToDTO);
    }

    @Override
    public List<RolDTO> findAll() {
        return rolRepository.findAll().stream()
                .map(this::convertRolToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RolDTO save(RolDTO rolDTO) {
        Rol rol = convertRolToEntity(rolDTO);
        Rol savedRol = rolRepository.save(rol);
        return convertRolToDTO(savedRol);
    }


    @Override
    public RolDTO update(RolDTO rolDTO) {
        Rol rol = convertRolToEntity(rolDTO);
        Rol updateRol = rolRepository.save(rol);
        return convertRolToDTO(updateRol);
    }

    @Override
    public void delete(Long id) {
        rolRepository.deleteById(id);
    }

    /*@Override
    public Optional<Rol> findById(Long id){
        return rolRepository.findById(id);
    }*/




    @Override
    public RolDTO convertRolToDTO(Rol rol){
        RolDTO dto = new RolDTO();
        dto.setId(rol.getRolId());
        dto.setRolNombre(rol.getRolNombre());
        return  dto;
    }

    @Override
    public Rol convertRolToEntity(RolDTO dto) {
        Rol rol = new Rol();
        rol.setRolId(dto.getId());
        rol.setRolNombre(dto.getRolNombre());
        return rol;  // Aquí devuelves la entidad 'rol'
    }


}
