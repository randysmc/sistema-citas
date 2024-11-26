import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReporteDTO } from '../models/reportedot.model';
import { ServiceReportDTO } from '../models/service-report.dto';
import { UsuarioReporteDTO } from '../models/usuario-reporte.dto';

import baserUrl from './helper';
@Injectable({
  providedIn: 'root'
})
export class ReporteService {
  

  constructor(private http: HttpClient) { }

  // Contar citas por cliente
  getCitasPorCliente(): Observable<ReporteDTO[]> {
    return this.http.get<ReporteDTO[]>(`${baserUrl}/reportes/citas/por-cliente`);
  }

  // Obtener usuario con más citas agendadas
  getUsuarioConMasCitasAgendadas(): Observable<ReporteDTO[]> {
    return this.http.get<ReporteDTO[]>(`${baserUrl}/reportes/citas/usuario-mas-agendadas`);
  }

  // Obtener usuario con más citas canceladas
  getUsuarioConMasCitasCanceladas(): Observable<ReporteDTO[]> {
    return this.http.get<ReporteDTO[]>(`${baserUrl}/reportes/citas/usuario-mas-canceladas`);
  }

  // Obtener horarios más solicitados
  obtenerHorariosMasSolicitados(): Observable<ReporteDTO> {
    return this.http.get<ReporteDTO>(`${baserUrl}/reportes/horarios-mas-solicitados`);
  }

  // Obtener frecuencia de uso por día de la semana
  obtenerFrecuenciaUsoPorDiaSemana(): Observable<ReporteDTO> {
    return this.http.get<ReporteDTO>(`${baserUrl}/reportes/frecuencia-uso-dia-semana`);
  }

  // Obtener recursos más y menos utilizados
  obtenerRecursosMasYMenosUtilizados(): Observable<ReporteDTO> {
    return this.http.get<ReporteDTO>(`${baserUrl}/reportes/recursos-mas-menos-utilizados`);
  }

  // Obtener tasa de cancelación por servicio
  obtenerTasaCancelacionPorServicio(): Observable<ReporteDTO> {
    return this.http.get<ReporteDTO>(`${baserUrl}/reportes/tasa-cancelacion-servicio`);
  }

  // Obtener lista de recursos utilizados
  obtenerListaRecursosUtilizados(): Observable<ReporteDTO> {
    return this.http.get<ReporteDTO>(`${baserUrl}/reportes/lista-recursos-utilizados`);
  }

  // Obtener lista de servicios utilizados
  obtenerListaServiciosUtilizados(): Observable<ReporteDTO> {
    return this.http.get<ReporteDTO>(`${baserUrl}/reportes/lista-servicios-utilizados`);
  }

  //obtener servicios mas utilizados
  getMostUsedServices(): Observable<ServiceReportDTO[]> {
    return this.http.get<ServiceReportDTO[]>(`${baserUrl}/reportes/servicio-mas-usado`);
  }

  // Obtener los servicios más utilizados por mes
  getMostUsedServicesByMonth(month: number, year: number): Observable<ServiceReportDTO[]> {
    return this.http.get<ServiceReportDTO[]>(`${baserUrl}/reportes/servicio-mas-usado-por-mes?month=${month}&year=${year}`);
  }

  // Obtener ingresos por servicio y mes
  getRevenueByServiceAndMonth(month: number, year: number): Observable<ServiceReportDTO[]> {
    return this.http.get<ServiceReportDTO[]>(`${baserUrl}/reportes/ingresos-por-servicio?month=${month}&year=${year}`);
  }

  // Obtener clientes con más citas
  getTopClientsByCitas(): Observable<UsuarioReporteDTO[]> {
    return this.http.get<UsuarioReporteDTO[]>(`${baserUrl}/reportes/clientes-mas-citas`);
  }

  // Obtener empleados con más citas
  getTopEmployeesByCitas(): Observable<UsuarioReporteDTO[]> {
    return this.http.get<UsuarioReporteDTO[]>(`${baserUrl}/reportes/empleados-mas-citas`);
  }

  // Obtener citas sin empleado
  getCitasWithNoEmpleado(): Observable<number> {
    return this.http.get<number>(`${baserUrl}/reportes/citas-sin-empleado`);
  }

  // Obtener empleados que más dinero generaron
  getTopEmployeesByRevenue(): Observable<UsuarioReporteDTO[]> {
    return this.http.get<UsuarioReporteDTO[]>(`${baserUrl}/reportes/empleado-mas-dinero`);
  }

  // Obtener clientes que más dinero generaron
  getTopClientsByRevenue(): Observable<UsuarioReporteDTO[]> {
    return this.http.get<UsuarioReporteDTO[]>(`${baserUrl}/reportes/cliente-mas-dinero`);
  }


}