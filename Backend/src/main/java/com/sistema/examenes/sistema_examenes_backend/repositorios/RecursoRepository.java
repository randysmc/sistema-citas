package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RecursoRepository extends JpaRepository<Recurso,Long> {
    Set<Negocio> findByNegocio(Negocio negocio);

    boolean existsByNombre(String nombre);
}
