package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura, Long> {


    public List<Factura> findByFacturaId(Long facturaId);

    List<Factura> findByCliente_Id(Long clienteId);

    public Factura findByCita_IdCita(Long citaId);

    @Query("SELECT s.nombre, COUNT(f) AS total FROM Factura f JOIN f.cita c JOIN c.servicio s GROUP BY s.nombre ORDER BY total DESC")
    List<Object[]> findMostUsedService();

    @Query("SELECT s.nombre, COUNT(f) AS total FROM Factura f " +
            "JOIN f.cita c " +
            "JOIN c.servicio s " +
            "WHERE FUNCTION('MONTH', f.fecha) = :month AND FUNCTION('YEAR', f.fecha) = :year " +
            "GROUP BY s.nombre " +
            "ORDER BY total DESC")
    List<Object[]> findMostUsedServiceByMonth(@Param("month") int month, @Param("year") int year);


    @Query("SELECT s.nombre, SUM(f.monto) AS totalRevenue FROM Factura f " +
            "JOIN f.cita c " +
            "JOIN c.servicio s " +
            "WHERE FUNCTION('MONTH', f.fecha) = :month AND FUNCTION('YEAR', f.fecha) = :year " +
            "GROUP BY s.nombre " +
            "ORDER BY totalRevenue DESC")
    List<Object[]> findRevenueByServiceAndMonth(@Param("month") int month, @Param("year") int year);


    @Query("SELECT c.nombre, c.apellido, COUNT(ci) AS totalCitas " +
            "FROM Cita ci JOIN ci.cliente c " +
            "GROUP BY c.id " +
            "ORDER BY totalCitas DESC")
    List<Object[]> findTopClientsByCitas();


    @Query("SELECT e.nombre, e.apellido, COUNT(ci) AS totalCitas " +
            "FROM Cita ci JOIN ci.empleado e " +
            "GROUP BY e.id " +
            "ORDER BY totalCitas DESC")
    List<Object[]> findTopEmployeesByCitas();


    @Query("SELECT COUNT(ci) FROM Cita ci WHERE ci.empleado IS NULL")
    Long countCitasWithNoEmpleado();


    @Query("SELECT e.nombre, e.apellido, SUM(f.monto) AS totalIngresos " +
            "FROM Factura f JOIN f.cita ci JOIN ci.empleado e " +
            "GROUP BY e.id " +
            "ORDER BY totalIngresos DESC")
    List<Object[]> findTopEmployeesByRevenue();


    @Query("SELECT c.nombre, c.apellido, SUM(f.monto) AS totalIngresos " +
            "FROM Factura f JOIN f.cita ci JOIN ci.cliente c " +
            "GROUP BY c.id " +
            "ORDER BY totalIngresos DESC")
    List<Object[]> findTopClientsByRevenue();


}