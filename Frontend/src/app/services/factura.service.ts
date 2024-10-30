import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import baserUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class FacturaService {

  constructor(private http: HttpClient) { }

  // Obtener todas las facturas
  obtenerFacturas(): Observable<any[]> {
    return this.http.get<any[]>(`${baserUrl}/facturas`);
  }

  // Crear una nueva factura
  crearFactura(factura: any): Observable<any> {
    return this.http.post<any>(`${baserUrl}/facturas/crear`, factura);
  }

  // Obtener factura por ID
  obtenerFacturaPorId(id: number): Observable<any> {
    return this.http.get<any>(`${baserUrl}/facturas/${id}`);
  }

  // Obtener facturas por usuario
  obtenerFacturasPorUsuario(usuarioId: number): Observable<any[]> {
    return this.http.get<any[]>(`${baserUrl}/facturas/usuario/${usuarioId}`);
  }

  // Actualizar factura
  actualizarFactura(factura: any): Observable<any> {
    return this.http.put<any>(`${baserUrl}/facturas`, factura);
  }

  // Eliminar factura
  eliminarFactura(id: number): Observable<void> {
    return this.http.delete<void>(`${baserUrl}/facturas/${id}`);
  }

  // Crear factura desde cita
  crearFacturaDesdeCita(citaId: number): Observable<any> {
    return this.http.post<any>(`${baserUrl}/facturas/crearDesdeCita/${citaId}`, null);
  }

  // Obtener factura por cita
  obtenerFacturaPorCita(citaId: number): Observable<any> {
    return this.http.get<any>(`${baserUrl}/facturas/cita/${citaId}`);
  }
}
