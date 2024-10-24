package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;


import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
import com.sistema.examenes.sistema_examenes_backend.excepciones.RecursoExistenteException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NegocioRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RecursoRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.RecursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecursoServiceImpl implements RecursoService {

    @Autowired
    private RecursoRepository recursoRepository;


    @Override
    public Optional<Recurso> findById(Long id) {
        return recursoRepository.findById(id);
    }

    @Override
    public List<Recurso> obtenerRecursos() {
        return recursoRepository.findAll();
    }

    @Override
    public Recurso obtenerRecurso(Long id) {
        return null;
    }


    @Override
    public Recurso guardarRecurso(Recurso recurso) {
        // Verificar si el nombre ya existe
        if (recursoRepository.existsByNombre(recurso.getNombre())) {
            throw new IllegalArgumentException("El nombre del recurso ya existe.");
        }
        return recursoRepository.save(recurso);
    }

    @Override
    public Recurso actualizaRecurso(Recurso recurso) {
        Recurso existingRecurso = recursoRepository.findById(recurso.getRecursoId())
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado con ID: " + recurso.getRecursoId()));

        // Solo actualiza el nombre si es diferente y no es nulo
        if (recurso.getNombre() != null && !recurso.getNombre().equals(existingRecurso.getNombre())) {
            // Verificar si el nuevo nombre ya existe en la base de datos
            if (recursoRepository.existsByNombre(recurso.getNombre())) {
                throw new RecursoExistenteException("El nombre del recurso ya existe");
            }
            existingRecurso.setNombre(recurso.getNombre());
        }

        // Solo actualiza la descripción si no es nula
        if (recurso.getDescripcion() != null) {
            existingRecurso.setDescripcion(recurso.getDescripcion());
        }

        // Solo actualiza el estado disponible si no es nulo
        if (recurso.getDisponible() != null) {
            existingRecurso.setDisponible(recurso.getDisponible());
        }

        // Solo actualiza el tipo si no es nulo
        if (recurso.getTipo() != null) {
            existingRecurso.setTipo(recurso.getTipo());
        }

        // Guardar los cambios en el repositorio
        return recursoRepository.save(existingRecurso);
    }


    @Override
    public void eliminarRecurso(Long id) {
        recursoRepository.deleteById(id);
    }

    @Override
    public List<Recurso> obtenerRecursosDisponibles() {
        return recursoRepository.findByDisponible(true); // Suponiendo que disponible es un booleano
    }

    @Override
    public List<Recurso> obtenerRecursosNoDisponibles() {
        return recursoRepository.findByDisponible(false);
    }

    @Override
    public void cambiarDisponibilidad(Long recursoId, boolean disponible) {
        Recurso recurso = recursoRepository.findById(recursoId)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado con ID: " + recursoId));

        // Cambiar la disponibilidad del recurso
        recurso.setDisponible(disponible);

        // Guardar el recurso actualizado
        recursoRepository.save(recurso);
    }





}
