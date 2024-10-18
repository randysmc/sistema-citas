package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.DTO.ServicioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ServicioService {

    Optional<Servicio> findById(Long id);

    List<Servicio> findAll();

    Servicio save(Servicio servicio);

    Servicio update(Servicio servicio);

    void delete(Long id);
}
