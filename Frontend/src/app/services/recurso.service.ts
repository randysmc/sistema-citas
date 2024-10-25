import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baserUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class RecursoService {

  constructor(private http:HttpClient) { 

  }

  public listarRecursos(){
    return this.http.get(`${baserUrl}/recursos/`)
  }

  public obtenerRecursoPorId(id: number) {
    return this.http.get(`${baserUrl}/recursos/${id}`);
  }

  public agregarRecurso(recurso:any){
    return this.http.post(`${baserUrl}/recursos/`, recurso)
  }

  public actualizarRecurso(id: number, recurso: any) {
    return this.http.put(`${baserUrl}/recursos/${id}`, recurso);
  }

  public obtenerRecursosDisponibles() {
    return this.http.get(`${baserUrl}/recursos/disponibles`);
  }

  public obtenerRecursosNoDisponibles() {
    return this.http.get(`${baserUrl}/recursos/no-disponibles`);
  }

  public habilitarRecurso(id: number) {
    return this.http.put(`${baserUrl}/recursos/habilitar/${id}`, {});
  }

  // Deshabilitar un recurso
  public deshabilitarRecurso(id: number) {
    return this.http.put(`${baserUrl}/recursos/deshabilitar/${id}`, {});
  }


}
