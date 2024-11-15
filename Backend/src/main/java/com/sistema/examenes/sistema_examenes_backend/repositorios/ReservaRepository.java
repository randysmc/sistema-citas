package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.entidades.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {


    public List<Reserva> findByEmpleadoIdAndFecha(Long empleadoId, LocalDate fecha);

    public List<Reserva> findByRecursoAndFecha(Recurso recurso, LocalDate fecha);

    public List<Reserva> findByEmpleadoAndFecha(Usuario empleado, LocalDate fecha);

    public List<Reserva> findByEmpleadoId(Long empleadoId);

    List<Reserva> findByActivaTrue();

    public Reserva findByCitaIdCita(Long idCita);

}
