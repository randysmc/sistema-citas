package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
import com.sistema.examenes.sistema_examenes_backend.entidades.Reserva;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    public List<Reserva> findByNegocioAndFecha(Long negocioId, LocalDate fecha);

    public List<Reserva> findByEmpleadoIdAndFecha(Long empleadoId, LocalDate fecha);

    List<Reserva> findByNegocioAndEmpleadoAndRecursoAndFecha(Negocio negocio, Usuario empleado, Recurso recurso, LocalDate fecha);
    List<Reserva> findByNegocioAndRecursoAndFecha(Negocio negocio, Recurso recurso, LocalDate fecha);

    List<Reserva> findByNegocioAndEmpleadoAndFecha(Negocio negocio, Usuario empleado, LocalDate fecha);


}
