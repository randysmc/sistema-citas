import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baserUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  constructor(private http:HttpClient) { 

  }

  public listarServicios(){
    return this.http.get(`${baserUrl}/servicios/`)
  }

  public obtenerServicioPorId(id: number) {
    return this.http.get(`${baserUrl}/servicios/${id}`);
  }

  public agregarServicio(servicio:any){
    return this.http.post(`${baserUrl}/servicios/`, servicio)
  }

  public actualizarServicio(id: number, servicio: any) {
    return this.http.put(`${baserUrl}/servicios/${id}`, servicio);
  }

  public obtenerServiciosDisponible() {
    return this.http.get(`${baserUrl}/servicios/disponibles`);
  }

  public obtenerServiciosNoDisponibles() {
    return this.http.get(`${baserUrl}/servicios/no-disponibles`);
  }

  public habilitarServicio(id: number) {
    return this.http.put(`${baserUrl}/servicios/habilitar/${id}`, {});
  }

  // Deshabilitar un recurso
  public deshabilitarServicio(id: number) {
    return this.http.put(`${baserUrl}/servicios/deshabilitar/${id}`, {});
  }

}
