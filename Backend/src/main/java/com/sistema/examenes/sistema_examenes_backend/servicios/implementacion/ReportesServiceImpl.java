package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.DTO.ReporteDTO;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import com.sistema.examenes.sistema_examenes_backend.entidades.Cita;
import com.sistema.examenes.sistema_examenes_backend.repositorios.CitaRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportesServiceImpl implements ReporteService {


    @Autowired
    private CitaRepository citaRepository;

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

    @Override
    public ReporteDTO obtenerFrecuenciaUsoPorDiaSemana() {
        List<Object[]> resultados = citaRepository.findUsageFrequencyByDayOfWeek();
        Map<String, Object> detalles = new HashMap<>();
        String[] dias = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};

        for (Object[] resultado : resultados) {
            Integer diaSemana = (Integer) resultado[0];
            Long total = (Long) resultado[1];
            detalles.put(dias[diaSemana - 1], total);
        }

        return new ReporteDTO(detalles);
    }

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









}
