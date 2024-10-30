package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    boolean existsByNombre(String nombre);

    // Obtener los servicios disponibles (disponible = true)
    public List<Servicio> findByDisponibleTrue();

    // Obtener los servicios no disponibles (disponible = false)
    public List<Servicio> findByDisponibleFalse();



}
