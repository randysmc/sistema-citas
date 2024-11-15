package com.sistema.examenes.sistema_examenes_backend.repositorios;

//import com.sistema.examenes.sistema_examenes_backend.DTO.NegocioDTO;
//import com.sistema.examenes.sistema_examenes_backend.DTO.RecursoDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RecursoRepository extends JpaRepository<Recurso,Long> {


    boolean existsByNombre(String nombre);

    public Optional<Recurso> findById(Long recursoId);

    public List<Recurso> findByDisponible(boolean disponible);
}
