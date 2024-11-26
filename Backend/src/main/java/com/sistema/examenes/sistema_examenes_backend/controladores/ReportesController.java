package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.DTO.ReporteDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.ServiceReportDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioReporteDTO;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import com.sistema.examenes.sistema_examenes_backend.servicios.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reportes")
@CrossOrigin("*")
public class ReportesController {

    @Autowired
    private ReporteService reporteService;


    // Endpoint para contar citas por cliente
    @GetMapping("/citas/por-cliente")
    public List<ReporteDTO> getCitasPorCliente() {
        return reporteService.contarCitasPorCliente();
    }



    @GetMapping("/citas/usuario-mas-agendadas")
    public List<ReporteDTO> getUsuarioConMasCitasAgendadas() {
        return reporteService.obtenerUsuarioConMasCitasAgendadas();
    }

    @GetMapping("/citas/usuario-mas-canceladas")
    public List<ReporteDTO> getUsuarioConMasCitasCanceladas() {
        return reporteService.obtenerUsuarioConMasCitasCanceladas();
    }

    // ReporteController.java
    @GetMapping("/horarios-mas-solicitados")
    public ResponseEntity<ReporteDTO> obtenerHorariosMasSolicitados() {
        return ResponseEntity.ok(reporteService.obtenerHorariosMasSolicitados());
    }

    // ReporteController.java
    /*@GetMapping("/frecuencia-uso-dia-semana")
    public ResponseEntity<ReporteDTO> obtenerFrecuenciaUsoPorDiaSemana() {
        return ResponseEntity.ok(reporteService.obtenerFrecuenciaUsoPorDiaSemana());
    }*/

    // ReporteController.java
    @GetMapping("/recursos-mas-menos-utilizados")
    public ResponseEntity<ReporteDTO> obtenerRecursosMasYMenosUtilizados() {
        return ResponseEntity.ok(reporteService.obtenerRecursosMasYMenosUtilizados());
    }

    @GetMapping("/tasa-cancelacion-servicio")
    public ResponseEntity<ReporteDTO> obtenerTasaCancelacionPorServicio() {
        return ResponseEntity.ok(reporteService.obtenerTasaCancelacionPorServicio());
    }

    @GetMapping("/lista-recursos-utilizados")
    public ResponseEntity<ReporteDTO> obtenerListaRecursosUtilizados() {
        return ResponseEntity.ok(reporteService.obtenerListaRecursosUtilizados());
    }

    @GetMapping("/lista-servicios-utilizados")
    public ResponseEntity<ReporteDTO> obtenerListaServiciosUtilizados() {
        return ResponseEntity.ok(reporteService.obtenerListaServiciosUtilizados());
    }


    @GetMapping("/servicio-mas-usado")
    public ResponseEntity<List<ServiceReportDTO>> getMostUsedServices() {
        List<ServiceReportDTO> report = reporteService.getMostUsedServices();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/servicio-mas-usado-por-mes")
    public ResponseEntity<List<ServiceReportDTO>> getMostUsedServicesByMonth(
            @RequestParam int month,
            @RequestParam int year) {
        List<ServiceReportDTO> report = reporteService.getMostUsedServicesByMonth(month, year);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/ingresos-por-servicio")
    public ResponseEntity<List<ServiceReportDTO>> getRevenueByServiceAndMonth(
            @RequestParam int month,
            @RequestParam int year) {
        List<ServiceReportDTO> report = reporteService.getRevenueByServiceAndMonth(month, year);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/clientes-mas-citas")
    public ResponseEntity<List<UsuarioReporteDTO>> getTopClientsByCitas() {
        return ResponseEntity.ok(reporteService.getTopClientsByCitas());
    }

    @GetMapping("/empleados-mas-citas")
    public ResponseEntity<List<UsuarioReporteDTO>> getTopEmployeesByCitas() {
        return ResponseEntity.ok(reporteService.getTopEmployeesByCitas());
    }

    @GetMapping("/citas-sin-empleado")
    public ResponseEntity<Long> getCitasWithNoEmpleado() {
        return ResponseEntity.ok(reporteService.getCitasWithNoEmpleado());
    }

    @GetMapping("/empleado-mas-dinero")
    public ResponseEntity<List<UsuarioReporteDTO>> getTopEmployeesByRevenue() {
        return ResponseEntity.ok(reporteService.getTopEmployeesByRevenue());
    }

    @GetMapping("/cliente-mas-dinero")
    public ResponseEntity<List<UsuarioReporteDTO>> getTopClientsByRevenue() {
        return ResponseEntity.ok(reporteService.getTopClientsByRevenue());
    }






}
