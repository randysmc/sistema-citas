package com.sistema.examenes.sistema_examenes_backend.servicios;

//import com.sistema.examenes.sistema_examenes_backend.DTO.NegocioDTO;
//import com.sistema.examenes.sistema_examenes_backend.DTO.RecursoDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;


import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RecursoService {

    public Optional<Recurso> findById(Long id);

    public List<Recurso> obtenerRecursos();

    public Recurso obtenerRecurso(Long id);

    public Recurso guardarRecurso(Recurso recurso);

    public Recurso actualizaRecurso(Recurso recurso);

    public void eliminarRecurso(Long id);

    public List<Recurso> obtenerRecursosDisponibles();

    public List<Recurso> obtenerRecursosNoDisponibles();

    void cambiarDisponibilidad(Long recursoId, boolean disponible);


}
