package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.entidades.DiaFestivo;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DiaFestivoRepository extends JpaRepository<DiaFestivo, Long> {
    Optional<DiaFestivo> findByNegocioAndFecha(Negocio negocio, LocalDate fecha);

    Optional<DiaFestivo> findByNegocioAndFechaAndAnyo(Negocio negocio, LocalDate fecha, Integer anyo);

}
