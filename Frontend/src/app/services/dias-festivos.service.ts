// dias-festivos.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baserUrl from './helper';
import { Observable } from 'rxjs';
import { DiaFestivo } from '../models/dia-festivo.model'; // Asegúrate de importar el modelo

@Injectable({
  providedIn: 'root'
})
export class DiasFestivosService {

  constructor(private http: HttpClient) { }

  obtenerDiasFestivos(): Observable<DiaFestivo[]> {
    return this.http.get<DiaFestivo[]>(`${baserUrl}/dias-festivos`);
  }
}
