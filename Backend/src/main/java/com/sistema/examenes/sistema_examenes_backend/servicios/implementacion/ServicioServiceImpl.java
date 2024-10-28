package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;


import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NegocioRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.ServicioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServicioServiceImpl  implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;


    @Override
    public Optional<Servicio> findById(Long id) {
        return servicioRepository.findById(id);
    }

    @Override
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio save(Servicio servicio) {
        // Verificamos si no hay otro servicio con ese nombre
        if (servicioRepository.existsByNombre(servicio.getNombre())) {
            throw new IllegalArgumentException("El nombre del servicio ya existe");
        }

        // Guardamos el servicio
        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio update(Servicio servicio) {
        Servicio existingServicio = servicioRepository.findById(servicio.getServicioId())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + servicio.getServicioId()));

        // Solo actualizamos los campos que vienen en la solicitud
        if (servicio.getNombre() != null) {
            if (!existingServicio.getNombre().equals(servicio.getNombre()) && servicioRepository.existsByNombre(servicio.getNombre())) {
                throw new IllegalArgumentException("El nombre del Servicio ya existe.");
            }
            existingServicio.setNombre(servicio.getNombre());
        }

        if (servicio.getDescripcion() != null) {
            existingServicio.setDescripcion(servicio.getDescripcion());
        }

        if (servicio.getDuracionServicio() != null) {
            existingServicio.setDuracionServicio(servicio.getDuracionServicio());
        }

        if (servicio.getPrecio() != null) {
            existingServicio.setPrecio(servicio.getPrecio());
        }

        return servicioRepository.save(existingServicio);
    }

    @Override
    public void delete(Long id) {
        servicioRepository.deleteById(id);
    }

    @Override
    public List<Servicio> findByDisponibleTrue() {
        return servicioRepository.findByDisponibleTrue();
    }

    @Override
    public List<Servicio> findByDisponibleFalse() {
        return servicioRepository.findByDisponibleFalse();
    }

    @Override
    public void cambiarDisponibilidad(Long servicioId, boolean disponible) {
        Servicio servicio = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + servicioId));

        servicio.setDisponible(disponible);
        servicioRepository.save(servicio);
    }

}
