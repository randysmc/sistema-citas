package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;


import com.sistema.examenes.sistema_examenes_backend.DTO.RecursoDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RecursoRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.RecursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class RecursoServiceImpl implements RecursoService {

    @Autowired
    private RecursoRepository recursoRepository;

    private RecursoDTO convertirARecursoDTO(Recurso recurso) {
        return new RecursoDTO(
                recurso.getRecursoId(),
                recurso.getNombre(),
                recurso.getDescripcion(),
                recurso.isDisponible(),
                recurso.getNegocio().getNegocioId()
        );
    }

    // Método para convertir RecursoDTO a entidad Recurso
    private Recurso convertirAEntidadRecurso(RecursoDTO recursoDTO, Negocio negocio) {
        Recurso recurso = new Recurso();
        recurso.setRecursoId(recursoDTO.getRecursoId());
        recurso.setNombre(recursoDTO.getNombre());
        recurso.setDescripcion(recursoDTO.getDescripcion());
        recurso.setDisponible(recursoDTO.isDisponible());
        recurso.setNegocio(negocio);
        return recurso;
    }


    @Override
    public Recurso agregarRercurso(Recurso recurso) {
        return recursoRepository.save(recurso);
    }

    @Override
    public Recurso actualizarRecurso(Recurso recurso) {
        return recursoRepository.save(recurso);
    }

    @Override
    public Set<Recurso> obtenerRecursos() {
        return new LinkedHashSet<>(recursoRepository.findAll());
    }

    @Override
    public Recurso obtenerRecurso(Long recursoId) {
        return recursoRepository.findById(recursoId).get();
    }

    @Override
    public void eliminarRecurso(Long recursoId) {
        Recurso recurso = new Recurso();
        recurso.setRecursoId(recursoId);
        recursoRepository.delete(recurso);

    }

    @Override
    public Set<Recurso> obtenerRecursosActivos() {
        return Set.of();
    }
}
