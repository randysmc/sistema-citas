package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.DTO.NegocioDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.RecursoDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;


import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RecursoService {

    Optional<Recurso> findById(Long id);

    List<Recurso> findAll();

    Recurso save(Recurso recurso);

    Recurso update(Recurso recurso);

    void delete(Long id);
}
