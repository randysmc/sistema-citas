import { Component, OnInit, Input } from '@angular/core';
import { DiasFestivosService } from 'src/app/services/dias-festivos.service';
import { DiaFestivo } from 'src/app/models/dia-festivo.model';
import { HorarioLaboral } from 'src/app/models/horario-laboral.model';
import { CalendarOptions, EventInput } from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import { HorariosLaboralesService } from 'src/app/services/horarios-laborales.service';
import { CitasService } from 'src/app/services/citas.service';
import { Cita } from 'src/app/models/cita.model';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

  @Input() mostrarDiasFestivos: boolean = false;
  @Input() mostrarHorarioLaboral: boolean = false;
  @Input() tipoCitas: string | null = null; 
  @Input() modoAgregarCita: boolean = false; 
  @Input() modoVerMisCitas: boolean = false; 


  calendarOptions: CalendarOptions = {
    initialView: 'dayGridMonth',
    plugins: [dayGridPlugin, interactionPlugin],
    events: [], 
  };

  diasFestivos: DiaFestivo[] = [];

  constructor(
    private diasFestivosService: DiasFestivosService,
    private horarioLaboralService: HorariosLaboralesService,
    private citasService: CitasService,
    private loginService: LoginService 
  ) {}

  ngOnInit(): void {
    this.cargarDiasFestivos();
    this.cargarHorariosLaborales();

    if (this.modoAgregarCita) {
      this.cargarCitas();
    } else if (this.modoVerMisCitas) {
      const userId = this.loginService.getUser().id; // Obtener ID del usuario autenticado
      this.cargarCitasPorUsuario(userId);
    } else {
      this.cargarCitasAgendadas();

    }
  }

  cargarCitasPorUsuario(usuarioId: number) {
    this.citasService.obtenerCitasPorUsuario(usuarioId).subscribe(
        (citas: Cita[]) => {
            const eventosCitas: EventInput[] = citas.map(cita => {
                const start = new Date(cita.fecha[0], cita.fecha[1] - 1, cita.fecha[2], cita.horaInicio[0], cita.horaInicio[1]);
                const end = new Date(cita.fecha[0], cita.fecha[1] - 1, cita.fecha[2], cita.horaFin[0], cita.horaFin[1]);

                return {
                    title: `Cita con: ${cita.cliente.nombre} ${cita.cliente.apellido} - ${cita.servicio.nombre}`,
                    start: start,
                    end: end,
                    color: 'green', // Puedes cambiar el color según lo que desees
                    allDay: false
                };
            });

            // Agrega los eventos de citas a calendarOptions.events
            this.calendarOptions.events = Array.isArray(this.calendarOptions.events)
                ? this.calendarOptions.events.concat(eventosCitas)
                : eventosCitas;
        },
        error => {
            console.log('Error al cargar las citas por usuario', error);
        }
    );
}



  cargarDiasFestivos() {
    this.diasFestivosService.obtenerDiasFestivos().subscribe(
        (diasFestivos: any[]) => { // Cambia `DiaFestivo[]` por `any[]` si no tienes el tipo definido
            this.diasFestivos = diasFestivos.map(dia => ({
                ...dia,
                fecha: new Date(dia.fecha[0], dia.fecha[1] - 1, dia.fecha[2]) // Convierte el arreglo a un objeto Date
            }));

            const eventosFestivos: EventInput[] = this.diasFestivos.map(dia => ({
                title: dia.descripcion,
                date: dia.fecha.toISOString().split('T')[0], // Convierte a formato YYYY-MM-DD
                color: 'red', // Color rojo para días festivos
            }));

            // Asegúrate de que calendarOptions.events sea un array
            this.calendarOptions.events = Array.isArray(this.calendarOptions.events) 
                ? this.calendarOptions.events.concat(eventosFestivos) 
                : eventosFestivos;
        },
        error => {
            console.log('Error al cargar los días festivos', error);
        }
    );
}

cargarCitas() {
    this.citasService.obtenerCitas().subscribe(
        (citas: Cita[]) => {
            const eventosCitas: EventInput[] = citas.map(cita => {
                const start = new Date(cita.fecha[0], cita.fecha[1] - 1, cita.fecha[2], cita.horaInicio[0], cita.horaInicio[1]);
                const end = new Date(cita.fecha[0], cita.fecha[1] - 1, cita.fecha[2], cita.horaFin[0], cita.horaFin[1]);

                return {
                    title: `Cita con: ${cita.cliente.nombre} ${cita.cliente.apellido} - ${cita.servicio.nombre}`,
                    start: start,
                    end: end,
                    color: 'green', // Puedes cambiar el color según lo que desees
                    allDay: false
                };
            });

            // Agrega los eventos de citas a calendarOptions.events
            this.calendarOptions.events = Array.isArray(this.calendarOptions.events)
                ? this.calendarOptions.events.concat(eventosCitas)
                : eventosCitas;
        },
        error => {
            console.log('Error al cargar las citas', error);
        }
    );
}


cargarCitasAgendadas() {
  this.citasService.obtenerCitasAgendadas().subscribe(
      (citas: Cita[]) => {
          const eventosCitas: EventInput[] = citas.map(cita => {
              const start = new Date(cita.fecha[0], cita.fecha[1] - 1, cita.fecha[2], cita.horaInicio[0], cita.horaInicio[1]);
              const end = new Date(cita.fecha[0], cita.fecha[1] - 1, cita.fecha[2], cita.horaFin[0], cita.horaFin[1]);

              return {
                  title: `Cita con: ${cita.cliente.nombre} ${cita.cliente.apellido} - ${cita.servicio.nombre}`,
                  start: start,
                  end: end,
                  color: 'green', // Puedes cambiar el color según lo que desees
                  allDay: false
              };
          });

          // Agrega los eventos de citas a calendarOptions.events
          this.calendarOptions.events = Array.isArray(this.calendarOptions.events)
              ? this.calendarOptions.events.concat(eventosCitas)
              : eventosCitas;
      },
      error => {
          console.log('Error al cargar las citas agendadas', error);
      }
  );
}





  cargarHorariosLaborales() {
    this.horarioLaboralService.obtenerHorariosLaborales().subscribe(
        (horarios: HorarioLaboral[]) => {
            const eventosLaborales: EventInput[] = [];
            const diasDeLaSemana: { [key: string]: number } = {
                LUNES: 1,
                MARTES: 2,
                MIERCOLES: 3,
                JUEVES: 4,
                VIERNES: 5,
                SABADO: 6,
                DOMINGO: 0
            };

            horarios.forEach(horario => {
                const diaLaboral = diasDeLaSemana[horario.dia as keyof typeof diasDeLaSemana]; // Conversión de día

                // Verificamos los próximos 10 lunes
                for (let i = 0; i < 10; i++) {
                    const fecha = new Date();
                    fecha.setDate(fecha.getDate() + (7 * i) + (diaLaboral - fecha.getDay())); // Calcular la fecha correcta

                    const start = new Date(fecha);
                    start.setHours(horario.horaInicio[0]);
                    start.setMinutes(horario.horaInicio[1]);

                    const end = new Date(fecha);
                    end.setHours(horario.horaFin[0]);
                    end.setMinutes(horario.horaFin[1]);

                    // Verificamos si hay un día festivo en esta fecha
                    const esDiaFestivo = this.diasFestivos.some(dia => {
                        return dia.fecha.toDateString() === start.toDateString(); // Comparación de fechas
                    });

                    if (!esDiaFestivo) {
                        eventosLaborales.push({
                            title: `Horario: ${horario.tipoHorario}`,
                            start: start,
                            end: end,
                            color: 'blue',
                            allDay: false
                        });
                    }
                }
            });

            // Asegúrate de que calendarOptions.events sea un array
            this.calendarOptions.events = Array.isArray(this.calendarOptions.events) 
                ? this.calendarOptions.events.concat(eventosLaborales) 
                : eventosLaborales;
        },
        error => {
            console.log('Error al cargar los horarios laborales', error);
        }
    );
  }

  private agregarEventosCitas(citas: Cita[], color: string) {
    const eventosCitas: EventInput[] = citas.map(cita => {
      const start = new Date(cita.fecha[0], cita.fecha[1] - 1, cita.fecha[2], cita.horaInicio[0], cita.horaInicio[1]);
      const end = new Date(cita.fecha[0], cita.fecha[1] - 1, cita.fecha[2], cita.horaFin[0], cita.horaFin[1]);

      return {
        title: `Cita con: ${cita.cliente.nombre} ${cita.cliente.apellido} - ${cita.servicio.nombre}`,
        start: start,
        end: end,
        color: color,
        allDay: false
      };
    });

    // Agrega los eventos de citas al calendario
    this.calendarOptions.events = Array.isArray(this.calendarOptions.events)
      ? this.calendarOptions.events.concat(eventosCitas)
      : eventosCitas;
  }

}
