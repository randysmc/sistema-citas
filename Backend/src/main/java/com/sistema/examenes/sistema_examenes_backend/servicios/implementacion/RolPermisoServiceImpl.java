package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.entidades.Permiso;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.entidades.RolPermiso;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityNotFoundException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.PermisoRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolPermisoRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolPermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolPermisoServiceImpl implements RolPermisoService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private RolPermisoRepository rolPermisoRepository;


    @Override
    public void asignarPermisosARol(Long rolId, List<Long> permisoId) {
        // Obtener el rol por ID
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new EntityNotFoundException("Rol", rolId));

        // Obtener los permisos por sus IDs
        List<Permiso> permisos = permisoRepository.findAllById(permisoId);

        // Verificar si todos los permisos fueron encontrados
        if (permisos.size() != permisoId.size()) {
            // Si la cantidad de permisos encontrados es diferente a la cantidad de IDs enviados,
            // significa que algunos permisos no se han encontrado
            List<Long> permisosNoEncontrados = permisoId.stream()
                    .filter(id -> permisos.stream().noneMatch(permiso -> permiso.getId().equals(id)))
                    .collect(Collectors.toList());

            throw new EntityNotFoundException("Permisos", permisosNoEncontrados);
        }

        // Verificar si ya existe alguna relación entre el rol y los permisos
        for (Permiso permiso : permisos) {
            // Comprobar si la relación ya existe
            boolean existeRelacion = rolPermisoRepository.existsByRolAndPermiso(rol, permiso);
            if (existeRelacion) {
                throw new RuntimeException("El permiso " + permiso.getNombre() + " ya está asignado al rol.");
            }
        }

        // Asignar los permisos al rol si no existen relaciones previas
        List<RolPermiso> rolPermisos = permisos.stream()
                .map(permiso -> new RolPermiso(null, rol, permiso))
                .collect(Collectors.toList());

        // Guardar los RolPermiso en la base de datos
        rolPermisoRepository.saveAll(rolPermisos);
    }


    @Override
    public List<Permiso> obtenerPermisosDeRol(Long rolId) {
        // Obtener el rol por su ID
        Rol rol = rolRepository.findById(rolId).orElseThrow(() -> new EntityNotFoundException("Rol", rolId));

        // Mapear los permisos asociados al rol
        // Aquí asumo que `rolPermisos` es una lista de objetos `RolPermiso` que contienen la relación con `Permiso`
        return rol.getRolPermisos().stream()
                .map(rolPermiso -> rolPermiso.getPermiso()) // Mapear RolPermiso a Permiso
                .collect(Collectors.toList()); // Recolectar en una lista
    }

}
