package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.entidades.Permiso;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityExistenteException;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityNotFoundException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.PermisoRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.PermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermisoServiceImpl implements PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    @Override
    public Optional<Permiso> findById(Long id) {
        Optional <Permiso> permiso = permisoRepository.findById(id);
        if(!permiso.isPresent()){
            throw new EntityNotFoundException("Permiso", id);
        }
        return permiso;
    }

    @Override
    public List<Permiso> findAll() {
        List<Permiso> permisos = permisoRepository.findAll();
        if(permisos.isEmpty()){
            throw new EntityNotFoundException("Permiso", "Ningun permiso encontrado");
        }
        return permisos;
    }

    @Override
    public Permiso save(Permiso permiso) {
        if(permisoRepository.findByNombre(permiso.getNombre()).isPresent()){
            throw new EntityExistenteException("Permiso", "nombre", permiso.getNombre());
        }
        return permisoRepository.save(permiso);
    }

    @Override
    public Permiso update(Permiso permiso) {
        Optional<Permiso> existingPermiso = permisoRepository.findById(permiso.getId());
        if(!existingPermiso.isPresent()){
            throw new EntityExistenteException("Permiso", "nombre", permiso.getNombre());
        }

        return permisoRepository.save(permiso);
    }

    @Override
    public void delete(Long id) {
        Optional<Permiso> existingPermiso = permisoRepository.findById(id);
        if(!existingPermiso.isPresent()){
            throw new EntityExistenteException("Rol", "id", id);
        }

        permisoRepository.deleteById(id);
    }
}
