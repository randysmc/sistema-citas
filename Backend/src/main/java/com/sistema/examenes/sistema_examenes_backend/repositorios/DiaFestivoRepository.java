package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.entidades.DiaFestivo;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaFestivoRepository extends JpaRepository<DiaFestivo, Long> {
    //Optional<DiaFestivo> findByNegocioAndFecha(Negocio negocio, LocalDate fecha);

    //Optional<DiaFestivo> findByNegocioAndFechaAndAnyo(Negocio negocio, LocalDate fecha, Integer anyo);

    //Aqui vamos a ver los días festivos de cada negocio
    //List<DiaFestivo> findByNegocio_NegocioId(Long negocioId);

    public Optional<DiaFestivo> findByFechaAndAnyo(LocalDate fecha, Integer anyo);

    public Optional<DiaFestivo> findByFecha(LocalDate fecha);

    // Obtener todos los días festivos recurrentes
    List<DiaFestivo> findByRecurrenteTrue();

    // Obtener todos los días festivos no recurrentes
    List<DiaFestivo> findByRecurrenteFalse();

}
