package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    Set<Negocio> findByNegocio(Negocio negocio);

}
