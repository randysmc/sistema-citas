package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.DTO.RecursoDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;


import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RecursoService {

    Optional<RecursoDTO> findById(Long id);

    List<RecursoDTO> findAll();

    RecursoDTO save(RecursoDTO recursoDTO);

    RecursoDTO update(RecursoDTO recursoDTO);

    void delete(Long id);

    Recurso convertRecursoToEntity(RecursoDTO recursoDTO);

    RecursoDTO convertRecursoToDTO(Recurso recurso);
}
