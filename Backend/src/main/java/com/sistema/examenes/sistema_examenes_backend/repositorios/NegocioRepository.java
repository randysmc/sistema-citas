package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NegocioRepository extends JpaRepository<Negocio, Long> {
    boolean existsByNombre(String nombre);

}
