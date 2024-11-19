package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.entidades.DiaFestivo;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaFestivoRepository extends JpaRepository<DiaFestivo, Long> {

    public Optional<DiaFestivo> findByFechaAndAnyo(LocalDate fecha, Integer anyo);

    public Optional<DiaFestivo> findByFecha(LocalDate fecha);

    List<DiaFestivo> findByRecurrenteTrue();

    List<DiaFestivo> findByRecurrenteFalse();

}
