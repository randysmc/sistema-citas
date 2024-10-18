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
    public Optional<Rol> findById(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    @Override
    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public Rol update(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public void delete(Long id) {
        rolRepository.deleteById(id);
    }


}
