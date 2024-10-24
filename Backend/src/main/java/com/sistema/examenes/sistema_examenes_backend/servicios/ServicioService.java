package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.DTO.ServicioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ServicioService {

    public Optional<Servicio> findById(Long id);

    public List<Servicio> findAll();

    public Servicio save(Servicio servicio);

    public Servicio update(Servicio servicio);

    public void delete(Long id);

    public List<Servicio> findByDisponibleTrue();

    public List<Servicio> findByDisponibleFalse();

    public void cambiarDisponibilidad(Long servicioId, boolean disponible);
}
