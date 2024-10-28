import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baserUrl from './helper';
import { Observable } from 'rxjs';
import { Cita } from '../models/cita.model';

@Injectable({
  providedIn: 'root',
})
export class CitasService {
  constructor(private http: HttpClient) {}

  public crearCita(cita: any) {
    return this.http.post(`${baserUrl}/citas/`, cita);
  }

  // Obtener todas las citas
  /*public obtenerCitas() {
    return this.http.get(`${baserUrl}/citas/`);
  }*/
  public obtenerCitas(): Observable<Cita[]> {
    return this.http.get<Cita[]>(`${baserUrl}/citas/`);
  }

  public obtenerCitasAgendadas(): Observable<Cita[]> {
    return this.http.get<Cita[]>(`${baserUrl}/citas/agendadas`);
  }
  
  public obtenerCitasCanceladas(): Observable<Cita[]> {
    return this.http.get<Cita[]>(`${baserUrl}/citas/canceladas`);
  }


  public obtenerCitasRealizadas() {
    return this.http.get<Cita[]>(`${baserUrl}/citas/realizadas`);
  }

  // Obtener citas por usuario
  public obtenerCitasPorUsuario(usuarioId: number) {
    return this.http.get(`${baserUrl}/citas/usuario/${usuarioId}`);
  }

  // Obtener cita por ID
  public obtenerCitaPorId(id: number) {
    return this.http.get(`${baserUrl}/citas/${id}`);
  }

  // Actualizar una cita
  public actualizarCita(cita: any) {
    return this.http.put(`${baserUrl}/citas/`, cita);
  }

  // Cancelar una cita
  public cancelarCita(id: number) {
    return this.http.put(`${baserUrl}/citas/${id}/cancelar`, {});
  }

  public completarCita(id: number) {
    return this.http.put(`${baserUrl}/citas/${id}/completar`, {});
  }

  // Obtener citas por empleado
  public obtenerCitasPorEmpleado(empleadoId: number) {
    return this.http.get(`${baserUrl}/citas/empleado/${empleadoId}`);
  }

  // Cancelar una cita

}
