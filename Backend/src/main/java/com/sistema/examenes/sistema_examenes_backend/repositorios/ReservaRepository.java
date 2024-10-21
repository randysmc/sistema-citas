package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    public List<Reserva> findByNegocioAndFecha(Long negocioId, LocalDate fecha);

    public List<Reserva> findByEmpleadoIdAndFecha(Long empleadoId, LocalDate fecha);

}
