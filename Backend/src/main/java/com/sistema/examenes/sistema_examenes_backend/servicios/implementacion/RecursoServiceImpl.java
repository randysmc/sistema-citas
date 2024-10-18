package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;


import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
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

    @Autowired
    private NegocioRepository negocioRepository;


    @Override
    public Optional<Recurso> findById(Long id) {
        return recursoRepository.findById(id);
    }

    @Override
    public List<Recurso> findAll() {
        return recursoRepository.findAll();
    }

    @Override
    public Recurso save(Recurso recurso) {
        // Verificar si el nombre ya existe
        if (recursoRepository.existsByNombre(recurso.getNombre())) {
            throw new IllegalArgumentException("El nombre del recurso ya existe.");
        }
        return recursoRepository.save(recurso);
    }

    @Override
    public Recurso update(Recurso recurso) {
        Recurso existingRecurso = recursoRepository.findById(recurso.getRecursoId())
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado con ID: " + recurso.getRecursoId()));

        // Actualiza los campos del recurso existente
        if (recurso.getNombre() != null) {
            if (!existingRecurso.getNombre().equals(recurso.getNombre()) && recursoRepository.existsByNombre(recurso.getNombre())) {
                throw new IllegalArgumentException("El nombre del recurso ya existe");
            }
            existingRecurso.setNombre(recurso.getNombre());
        }
        if (recurso.getDescripcion() != null) {
            existingRecurso.setDescripcion(recurso.getDescripcion());
        }
        existingRecurso.setDisponible(recurso.isDisponible());

        return recursoRepository.save(existingRecurso);
    }

    @Override
    public void delete(Long id) {
        recursoRepository.deleteById(id);
    }


}
