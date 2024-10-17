package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;


import java.util.Set;

public interface RecursoService {

    Recurso agregarRercurso(Recurso recurso);

    Recurso actualizarRecurso(Recurso recurso);

    Set<Recurso> obtenerRecursos();

    Recurso obtenerRecurso(Long recursoId);

    void eliminarRecurso(Long recursoId);

    Set<Recurso> obtenerRecursosActivos();
}
