package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.DTO.NegocioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;

import java.util.List;
import java.util.Optional;

public interface NegocioService {

    Optional<NegocioDTO> findById(Long id);

    //NegocioDTO findById(Long id);

    List<NegocioDTO> findAll();

    NegocioDTO save(NegocioDTO negocioDTO);

    NegocioDTO update(NegocioDTO negocioDTO);

    void delete(Long id);

    Negocio convertNegocioToEntity(NegocioDTO negocioDTO);

    NegocioDTO convertNegocioToDTO(Negocio negocio);
}
