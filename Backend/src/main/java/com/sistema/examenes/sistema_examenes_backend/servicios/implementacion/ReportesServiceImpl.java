package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.DTO.ReporteDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.ServiceReportDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioReporteDTO;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import com.sistema.examenes.sistema_examenes_backend.entidades.Cita;
import com.sistema.examenes.sistema_examenes_backend.repositorios.CitaRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.FacturaRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportesServiceImpl implements ReporteService {


    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    public List<ReporteDTO> contarCitasPorCliente() {
        List<Map<String, Object>> datos = citaRepository.contarCitasPorCliente();
        return datos.stream()
                .map(ReporteDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReporteDTO> obtenerCitasPorEstado() {
        List<Map<String, Object>> datos = citaRepository.obtenerCitasPorEstado();
        return datos.stream()
                .map(ReporteDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReporteDTO> obtenerUsuarioConMasCitasAgendadas() {
        List<Map<String, Object>> datos = citaRepository.obtenerUsuarioConMasCitasAgendadas(EstadoCita.AGENDADA);
        return datos.stream()
                .map(ReporteDTO::new)  // Mapeo de Map<String, Object> a ReporteDTO
                .collect(Collectors.toList());
    }

    @Override
    public List<ReporteDTO> obtenerUsuarioConMasCitasCanceladas() {
        List<Map<String, Object>> datos = citaRepository.obtenerUsuarioConMasCitasCanceladas(EstadoCita.CANCELADA);
        return datos.stream()
                .map(ReporteDTO::new)  // Mapeo de Map<String, Object> a ReporteDTO
                .collect(Collectors.toList());
    }

    @Override
    public ReporteDTO obtenerHorariosMasSolicitados() {
        List<Object[]> resultados = citaRepository.findMostRequestedHours();
        Map<String, Object> detalles = new HashMap<>();

        for (Object[] resultado : resultados) {
            Integer hora = (Integer) resultado[0];
            Long total = (Long) resultado[1];
            detalles.put(hora + ":00", total);
        }

        return new ReporteDTO(detalles);
    }

    /*@Override
    public ReporteDTO obtenerFrecuenciaUsoPorDiaSemana() {
        List<Object[]> resultados = citaRepository.findUsageFrequencyByDayOfWeek();
        Map<String, Object> detalles = new HashMap<>();
        String[] dias = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};

        for (Object[] resultado : resultados) {
            Integer diaSemana = (Integer) resultado[0];
            Long total = (Long) resultado[1];
            System.out.println("Día de la semana: " + diaSemana + ", Total: " + total);  // Depuración
            detalles.put(dias[diaSemana - 1], total);
        }

        return new ReporteDTO(detalles);
    }*/


    @Override
    public ReporteDTO obtenerRecursosMasYMenosUtilizados() {
        List<Object[]> resultados = citaRepository.findResourceUsage();
        Map<String, Object> detalles = new HashMap<>();

        if (!resultados.isEmpty()) {
            detalles.put("Mas utilizado", resultados.get(0)[0]);
            detalles.put("Menos utilizado", resultados.get(resultados.size() - 1)[0]);
        }

        return new ReporteDTO(detalles);
    }

    @Override
    public ReporteDTO obtenerTasaCancelacionPorServicio() {
        List<Object[]> resultados = citaRepository.findCancellationRateByService();
        Map<String, Object> detalles = new HashMap<>();

        for (Object[] resultado : resultados) {
            String servicio = (String) resultado[0];
            Long canceladas = (Long) resultado[1];
            Long total = (Long) resultado[2];
            double tasaCancelacion = total > 0 ? (canceladas.doubleValue() / total) * 100 : 0;
            detalles.put(servicio, tasaCancelacion + "%");
        }

        return new ReporteDTO(detalles);
    }

    @Override
    public ReporteDTO obtenerListaRecursosUtilizados() {
        List<Object[]> resultados = citaRepository.findAllResourceUsage();
        Map<String, Object> detalles = new LinkedHashMap<>();

        for (Object[] resultado : resultados) {
            String recurso = (String) resultado[0];
            Long total = (Long) resultado[1];
            detalles.put(recurso, total);
        }

        return new ReporteDTO(detalles);
    }

    @Override
    public ReporteDTO obtenerListaServiciosUtilizados() {
        List<Object[]> resultados = citaRepository.findAllServiceUsage();
        Map<String, Object> detalles = new LinkedHashMap<>();

        for (Object[] resultado : resultados) {
            String servicio = (String) resultado[0];
            Long total = (Long) resultado[1];
            detalles.put(servicio, total);
        }

        return new ReporteDTO(detalles);
    }



    @Override
    public List<ServiceReportDTO> getMostUsedServices() {
        List<Object[]> results = facturaRepository.findMostUsedService();
        List<ServiceReportDTO> serviceReportDTOs = new ArrayList<>();

        for (Object[] result : results) {
            String serviceName = (String) result[0];
            Long usageCount = ((Number) result[1]).longValue();
            serviceReportDTOs.add(new ServiceReportDTO(serviceName, usageCount));
        }

        return serviceReportDTOs;
    }

    @Override
    public List<ServiceReportDTO> getMostUsedServicesByMonth(int month, int year) {
        List<Object[]> results = facturaRepository.findMostUsedServiceByMonth(month, year);
        List<ServiceReportDTO> serviceReportDTOs = new ArrayList<>();

        for (Object[] result : results) {
            String serviceName = (String) result[0];
            Long usageCount = ((Number) result[1]).longValue();
            serviceReportDTOs.add(new ServiceReportDTO(serviceName, usageCount));
        }

        return serviceReportDTOs;
    }

    @Override
    public List<ServiceReportDTO> getRevenueByServiceAndMonth(int month, int year) {
        List<Object[]> results = facturaRepository.findRevenueByServiceAndMonth(month, year);
        List<ServiceReportDTO> serviceReportDTOs = new ArrayList<>();

        for (Object[] result : results) {
            String serviceName = (String) result[0];
            Long totalRevenue = ((Number) result[1]).longValue(); // Aquí tratamos el monto como un valor entero.
            serviceReportDTOs.add(new ServiceReportDTO(serviceName, totalRevenue));
        }

        return serviceReportDTOs;
    }

    @Override
    public List<UsuarioReporteDTO> getTopClientsByCitas() {
        List<Object[]> results = facturaRepository.findTopClientsByCitas();
        return results.stream()
                .map(result -> new UsuarioReporteDTO((String) result[0], (String) result[1], ((Number) result[2]).longValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioReporteDTO> getTopEmployeesByCitas() {
        List<Object[]> results = facturaRepository.findTopEmployeesByCitas();
        return results.stream()
                .map(result -> new UsuarioReporteDTO((String) result[0], (String) result[1], ((Number) result[2]).longValue()))
                .collect(Collectors.toList());
    }

    @Override
    public Long getCitasWithNoEmpleado() {
        return facturaRepository.countCitasWithNoEmpleado();
    }

    @Override
    public List<UsuarioReporteDTO> getTopEmployeesByRevenue() {
        List<Object[]> results = facturaRepository.findTopEmployeesByRevenue();
        return results.stream()
                .map(result -> new UsuarioReporteDTO((String) result[0], (String) result[1], ((Number) result[2]).longValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioReporteDTO> getTopClientsByRevenue() {
        List<Object[]> results = facturaRepository.findTopClientsByRevenue();
        return results.stream()
                .map(result -> new UsuarioReporteDTO((String) result[0], (String) result[1], ((Number) result[2]).longValue()))
                .collect(Collectors.toList());
    }












}
