import { Component, OnInit } from '@angular/core';
import { HorariosLaboralesService } from 'src/app/services/horarios-laborales.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Location } from '@angular/common';

@Component({
  selector: 'app-horarios-form',
  templateUrl: './horarios-form.component.html',
  styleUrls: ['./horarios-form.component.css']
})

export class HorariosFormComponent implements OnInit {
  horarioLaboralId: number | null = null;
  horarioLaboral = {
    dia: '',
    horaInicio: '',
    horaFin: '',
    tipoHorario: ''
  };

  constructor(
    private horarioLaboralService: HorariosLaboralesService,
    private snack: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  ngOnInit() {
    // Verificar si hay un id en la ruta para editar el horario
    this.horarioLaboralId = this.route.snapshot.params['id'] || null;

    if (this.horarioLaboralId) {
      this.cargarHorario();
    }
  }

  // Cargar el horario para editarlo
  cargarHorario() {
    this.horarioLaboralService.obtenerHorarioPorId(this.horarioLaboralId!).subscribe(
      (data: any) => {
        this.horarioLaboral = {
          ...data,
          // Convierte las horas en formato "HH:mm" para mostrarlas en el formulario
          horaInicio: this.convertirHoraArrayAString(data.horaInicio),
          horaFin: this.convertirHoraArrayAString(data.horaFin),
        };
      },
      (error) => {
        Swal.fire('Error', 'Error al cargar el horario a editar', 'error');
        this.router.navigate(['/admin/horarios-laborales']);
      }
    );
  }



  convertirHoraArrayAString(horaArray: number[]): string {
    const [hora, minuto] = horaArray;
    return `${hora.toString().padStart(2, '0')}:${minuto.toString().padStart(2, '0')}`;
  }
  
  // Convierte una cadena "HH:mm" a un array [hora, minuto]
  convertirHoraStringAArray(horaString: string): number[] {
    const [hora, minuto] = horaString.split(':').map(Number);
    return [hora, minuto];
  }



  onSubmit() {
    if (!this.horarioLaboral.horaInicio || !this.horarioLaboral.horaFin) {
      this.snack.open('Por favor, complete todos los campos.', 'Cerrar', { duration: 3000 });
      return;
    }
  
    const horarioData = {
      ...this.horarioLaboral,
      // Convierte las cadenas "HH:mm" de vuelta a arrays [hora, minuto]
      horaInicio: this.convertirHoraStringAArray(this.horarioLaboral.horaInicio),
      horaFin: this.convertirHoraStringAArray(this.horarioLaboral.horaFin),
    };
  
    if (this.horarioLaboralId) {
      this.horarioLaboralService.actualizarHorarioLaboral(this.horarioLaboralId, horarioData).subscribe(
        () => {
          Swal.fire('Horario actualizado', 'El horario laboral se ha actualizado exitosamente.', 'success')
            .then(() => this.router.navigate(['/admin/horarios-laborales']));
        },
        () => {
          Swal.fire('Error', 'No se pudo actualizar el horario laboral.', 'error');
        }
      );
    } else {
      this.horarioLaboralService.agregarHorarioLaboral(horarioData).subscribe(
        () => {
          Swal.fire('Horario agregado', 'El horario laboral se ha agregado exitosamente.', 'success')
            .then(() => this.router.navigate(['/admin/horarios-laborales']));
        },
        () => {
          Swal.fire('Error', 'Ya existe un horario configurado para este día y periodo.', 'error');
        }
      );
    }
  }

  regresar(){
    this.location.back();
  }
}
