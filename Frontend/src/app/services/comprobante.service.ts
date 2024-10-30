import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import baserUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class ComprobanteService {

  constructor(private http: HttpClient) { }

  // Obtener todos los comprobantes
  obtenerComprobantes(): Observable<any[]> {
    return this.http.get<any[]>(`${baserUrl}/comprobantes`);
  }

  // Crear un nuevo comprobante
  crearComprobante(comprobante: any): Observable<any> {
    return this.http.post<any>(`${baserUrl}/comprobantes/crear`, comprobante);
  }

  // Obtener comprobantes por cliente
  obtenerComprobantesPorCliente(clienteId: number): Observable<any[]> {
    return this.http.get<any[]>(`${baserUrl}/comprobantes/cliente/${clienteId}`);
  }

  // Obtener comprobantes por estado
  obtenerComprobantesPorEstado(estado: string): Observable<any[]> {
    return this.http.get<any[]>(`${baserUrl}/comprobantes/estado/${estado}`);
  }

  // Crear comprobante por cita
  crearComprobantePorCita(citaId: number, comprobante: any): Observable<any> {
    return this.http.post<any>(`${baserUrl}/comprobantes/cita/${citaId}`, comprobante);
  }
}
