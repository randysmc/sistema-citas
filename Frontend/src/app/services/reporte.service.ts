import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReporteDTO } from '../models/reportedot.model'; // Asegúrate de que este DTO esté definido en tu proyecto
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
}