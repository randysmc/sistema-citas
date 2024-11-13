package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityExistenteException;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityNotFoundException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImplementacion implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Optional<Rol> findById(Long id) {
        Optional<Rol> rol = rolRepository.findById(id);
        if (!rol.isPresent()) {
            throw new EntityNotFoundException("Rol", id); // Usamos EntityNotFoundException si no se encuentra el rol
        }
        return rol;
    }

    @Override
    public List<Rol> findAll() {
        List<Rol> roles = rolRepository.findAll();
        if (roles.isEmpty()) {
            throw new EntityNotFoundException("Rol", "Ningún rol encontrado");
        }
        return roles;
    }

    @Override
    public Rol save(Rol rol) {
        // Validar si ya existe un rol con el mismo nombre
        if (rolRepository.findByRolNombre(rol.getRolNombre()).isPresent()) {
            throw new EntityExistenteException("Rol", "rolNombre", rol.getRolNombre());
        }

        // Si no existe, guardamos el rol
        return rolRepository.save(rol);
    }

    @Override
    public Rol update(Rol rol) {
        // Verificamos si el rol existe antes de intentar actualizarlo
        Optional<Rol> existingRol = rolRepository.findById(rol.getRolId());
        if (!existingRol.isPresent()) {
            throw new EntityExistenteException("Rol", "rolId", rol.getRolId());
        }

        // Actualizamos el rol
        return rolRepository.save(rol);
    }

    @Override
    public void delete(Long id) {
        // Verificamos si el rol existe antes de eliminarlo
        Optional<Rol> existingRol = rolRepository.findById(id);
        if (!existingRol.isPresent()) {
            throw new EntityExistenteException("Rol", "rolId", id);
        }

        rolRepository.deleteById(id);
    }
}
