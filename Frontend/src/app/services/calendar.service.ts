import { Injectable } from '@angular/core';
import { DiasFestivosService } from './dias-festivos.service';
import { HorariosLaboralesService } from './horarios-laborales.service';
import { CitasService } from './citas.service';
import { DiaFestivo } from '../models/dia-festivo.model';
import { HorarioLaboral } from '../models/horario-laboral.model';
import { Cita } from '../models/cita.model';
import { Observable, forkJoin } from 'rxjs';
import { map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class CalendarService {

  constructor(
    private diasFestivosService: DiasFestivosService,
    private horarioLaboralService: HorariosLaboralesService,
    private citasService: CitasService
  ) {}
}
