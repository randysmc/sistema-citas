import { Component } from '@angular/core';
import { HorariosLaboralesService } from 'src/app/services/horarios-laborales.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-horario-laboral',
  templateUrl: './add-horario-laboral.component.html',
  styleUrls: ['./add-horario-laboral.component.css']
})
export class AddHorarioLaboralComponent {
  horarioLaboral = {
    dia: '',
    horaInicio: '',
    horaFin: '',
    tipoHorario: ''
  };

  constructor(private horariosLaboralesService: HorariosLaboralesService, private router: Router) {}

  onSubmit() {
    // Convierte la horaInicio y horaFin a un arreglo [hora, minuto]
    const horaInicioArray = this.horarioLaboral.horaInicio.split(':').map(Number);
    const horaFinArray = this.horarioLaboral.horaFin.split(':').map(Number);

    const horarioData = {
      dia: this.horarioLaboral.dia,
      horaInicio: horaInicioArray,
      horaFin: horaFinArray,
      tipoHorario: this.horarioLaboral.tipoHorario
    };

    // Llama al servicio para agregar el horario laboral
    this.horariosLaboralesService.agregarHorarioLaboral(horarioData).subscribe(
      response => {
        Swal.fire({
          title: 'Horario agregado',
          text: 'El horario laboral se ha agregado exitosamente.',
          icon: 'success',
          confirmButtonText: 'Aceptar',
          customClass: {
            confirmButton: 'custom-confirm-button'
          }
        }).then(() => {
          this.router.navigate(['/admin/calendario']);
        });
      },
      error => {
        Swal.fire({
          title: 'Error al agregar horario',
          text: 'Ya existe un horario configurado para este día y periodo.',
          icon: 'error',
          confirmButtonText: 'Intentar de nuevo',
          customClass: {
            confirmButton: 'custom-confirm-button'
          }
        });
      }
    );
  }
}
