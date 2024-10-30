import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import baserUrl from './helper';
import { Reserva } from '../models/reserva.model'; // Asegúrate de importar el modelo

@Injectable({
  providedIn: 'root'
})
export class ReservasService {
  private baseUrl = `${baserUrl}/reservas`;

  constructor(private http: HttpClient) { }

  public crearReserva(reserva: Reserva): Observable<Reserva> {
    return this.http.post<Reserva>(this.baseUrl, reserva);
  }

  public obtenerReservas(): Observable<Reserva[]> {
    return this.http.get<Reserva[]>(this.baseUrl);
  }

  public obtenerReservaPorId(id: number): Observable<Reserva> {
    return this.http.get<Reserva>(`${this.baseUrl}/${id}`);
  }

  public actualizarReserva(reserva: Reserva): Observable<Reserva> {
    return this.http.put<Reserva>(this.baseUrl, reserva);
  }

  public cancelarReserva(id: number): Observable<Reserva> {
    return this.http.put<Reserva>(`${this.baseUrl}/${id}/cancelar`, {});
  }

  public obtenerReservasPorUsuario(usuarioId: number): Observable<Reserva[]> {
    return this.http.get<Reserva[]>(`${this.baseUrl}/usuario/${usuarioId}`);
  }

  public obtenerReservasPorEmpleado(empleadoId: number): Observable<Reserva[]> {
    return this.http.get<Reserva[]>(`${this.baseUrl}/empleado/${empleadoId}`);
  }

  public obtenerReservasActivas(): Observable<Reserva[]> {
    return this.http.get<Reserva[]>(`${this.baseUrl}/activas`);
  }
}
