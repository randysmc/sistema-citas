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

  public agregarRecurso(recurso:any){
    return this.http.post(`${baserUrl}/recursos/`, recurso)
  }
}
