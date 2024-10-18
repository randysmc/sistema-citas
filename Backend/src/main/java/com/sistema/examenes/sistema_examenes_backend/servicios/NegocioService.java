package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.DTO.NegocioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;

import java.util.List;
import java.util.Optional;

public interface NegocioService {

    Optional<Negocio> findById(Long id);

    //NegocioDTO findById(Long id);

    List<Negocio> findAll();

    Negocio save(Negocio negocio);

    Negocio update(Negocio negocio);

    void delete(Long id);

    //List<NegocioDTO> obtenerServiciosPorNegocio(Long negocioId);

    //Negocio convertNegocioToEntity(NegocioDTO negocioDTO);

    //NegocioDTO convertNegocioToDTO(Negocio negocio);
}
