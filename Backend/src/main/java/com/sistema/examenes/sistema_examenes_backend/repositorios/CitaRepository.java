package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import com.sistema.examenes.sistema_examenes_backend.entidades.Cita;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    public List<Cita> findByClienteId(Long clienteId);

    public List<Cita> findByEmpleadoId(Long empleadoId);

    public List<Cita> findByEstado(EstadoCita estadoCita);



    // Método para contar citas por cliente
    @Query("SELECT new map(c.cliente.nombre as nombre, c.cliente.apellido as apellido, COUNT(c) as numeroCitas) " +
            "FROM Cita c GROUP BY c.cliente.id")
    List<Map<String, Object>> contarCitasPorCliente();


    // Método para contar citas por estado
    @Query("SELECT new map(c.estado as estado, COUNT(c) as numeroCitas) " +
            "FROM Cita c GROUP BY c.estado")
    List<Map<String, Object>> obtenerCitasPorEstado();



    @Query("SELECT new map(c.cliente.nombre as nombre, c.cliente.apellido as apellido, COUNT(c) as numeroCitas) " +
            "FROM Cita c WHERE c.estado = :estado " +
            "GROUP BY c.cliente.id " +
            "ORDER BY numeroCitas DESC")
    List<Map<String, Object>> obtenerUsuarioConMasCitasAgendadas(@Param("estado") EstadoCita estado);

    @Query("SELECT new map(c.cliente.nombre as nombre, c.cliente.apellido as apellido, COUNT(c) as numeroCitas) " +
            "FROM Cita c WHERE c.estado = :estado " +
            "GROUP BY c.cliente.id " +
            "ORDER BY numeroCitas DESC")
    List<Map<String, Object>> obtenerUsuarioConMasCitasCanceladas(@Param("estado") EstadoCita estado);

    @Query("SELECT FUNCTION('HOUR', c.horaInicio) AS hora, COUNT(c) AS total FROM Cita c GROUP BY FUNCTION('HOUR', c.horaInicio) ORDER BY total DESC")
    List<Object[]> findMostRequestedHours();


    @Query("SELECT FUNCTION('DAYOFWEEK', c.fecha) AS dia, COUNT(c) AS total FROM Cita c GROUP BY FUNCTION('DAYOFWEEK', c.fecha) ORDER BY dia")
    List<Object[]> findUsageFrequencyByDayOfWeek();

    @Query("SELECT c.recurso.nombre, COUNT(c) AS total FROM Cita c GROUP BY c.recurso.nombre ORDER BY total DESC")
    List<Object[]> findResourceUsage();

    @Query("SELECT c.servicio.nombre, SUM(CASE WHEN c.estado = 'CANCELADA' THEN 1 ELSE 0 END) AS canceladas, COUNT(c) AS total FROM Cita c GROUP BY c.servicio.nombre")
    List<Object[]> findCancellationRateByService();

    @Query("SELECT c.recurso.nombre, COUNT(c) AS total FROM Cita c GROUP BY c.recurso.nombre ORDER BY total DESC")
    List<Object[]> findAllResourceUsage();

    @Query("SELECT c.servicio.nombre, COUNT(c) AS total FROM Cita c GROUP BY c.servicio.nombre ORDER BY total DESC")
    List<Object[]> findAllServiceUsage();






}
