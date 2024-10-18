package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.DTO.ServicioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ServicioService {

    Optional<ServicioDTO> findById(Long id);

    List<ServicioDTO> findAll();

    ServicioDTO save(ServicioDTO servicioDTO);

    ServicioDTO update(ServicioDTO servicioDTO);

    void delete(Long id);

    Servicio convertServicioToEntity(ServicioDTO servicioDTO);

    ServicioDTO convertServicioToDTO(Servicio servicio);
}
