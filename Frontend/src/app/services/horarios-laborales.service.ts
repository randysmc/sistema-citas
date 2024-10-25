import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HorarioLaboral } from '../models/horario-laboral.model';
import baserUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class HorariosLaboralesService {

  constructor(private http:HttpClient) { }

  obtenerHorariosLaborales():Observable<HorarioLaboral[]>{
    return this.http.get<HorarioLaboral[]>(`${baserUrl}/horarios-laborales`)
  }
}
