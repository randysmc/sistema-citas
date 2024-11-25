import { Component, OnInit } from '@angular/core';
import { HorariosLaboralesService } from 'src/app/services/horarios-laborales.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { MatSnackBar } from '@angular/material/snack-bar';

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
    private route: ActivatedRoute
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
        this.horarioLaboral = data; // Cargar los datos del horario en el formulario
      },
      (error) => {
        Swal.fire('Error', 'Error al cargar el horario a editar', 'error');
      }
    );
  }

  // Lógica para el envío del formulario
  onSubmit() {
    const horarioData = {
      dia: this.horarioLaboral.dia,
      horaInicio: this.horarioLaboral.horaInicio,
      horaFin: this.horarioLaboral.horaFin,
      tipoHorario: this.horarioLaboral.tipoHorario
    };

    if (this.horarioLaboralId) {
      // Si hay un ID, actualizar el horario
      this.horarioLaboralService.actualizarHorarioLaboral(this.horarioLaboralId, horarioData).subscribe(
        (response) => {
          Swal.fire({
            title: 'Horario actualizado',
            text: 'El horario laboral se ha actualizado exitosamente.',
            icon: 'success',
            confirmButtonText: 'Aceptar',
            customClass: { confirmButton: 'custom-confirm-button' }
          }).then(() => {
            this.router.navigate(['/admin/calendario']);
          });
        },
        (error) => {
          Swal.fire({
            title: 'Error al actualizar',
            text: 'No se pudo actualizar el horario laboral.',
            icon: 'error',
            confirmButtonText: 'Intentar de nuevo',
            customClass: { confirmButton: 'custom-confirm-button' }
          });
        }
      );
    } else {
      // Si no hay ID, agregar un nuevo horario
      this.horarioLaboralService.agregarHorarioLaboral(horarioData).subscribe(
        (response) => {
          Swal.fire({
            title: 'Horario agregado',
            text: 'El horario laboral se ha agregado exitosamente.',
            icon: 'success',
            confirmButtonText: 'Aceptar',
            customClass: { confirmButton: 'custom-confirm-button' }
          }).then(() => {
            this.router.navigate(['/admin/calendario']);
          });
        },
        (error) => {
          Swal.fire({
            title: 'Error al agregar horario',
            text: 'Ya existe un horario configurado para este día y periodo.',
            icon: 'error',
            confirmButtonText: 'Intentar de nuevo',
            customClass: { confirmButton: 'custom-confirm-button' }
          });
        }
      );
    }
  }
}
