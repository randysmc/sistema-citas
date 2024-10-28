import { Component, Input, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { RecursoService } from 'src/app/services/recurso.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-recursos',
  templateUrl: './add-recursos.component.html',
  styleUrls: ['./add-recursos.component.css']
})
export class AddRecursosComponent implements OnInit {

  @Input() recursoEdit: any; // Para recibir el recurso a editar
  recurso = {
    nombre: '',
    descripcion: '',
    disponible: true,
    tipo: 'PERSONAL' // Valor por defecto
  };

  tipos = ['PERSONAL', 'INSTALACION']; // Opciones del selector para el tipo

  constructor(
    private recursoService: RecursoService,
    private snack: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Si existe un recurso para editar, carga sus datos
    if (this.recursoEdit) {
      this.recurso = {
        ...this.recursoEdit
      };
    }
  }

  formSubmit() {
    if (this.recurso.nombre.trim() === '' || this.recurso.nombre == null) {
      this.snack.open("El título es requerido !!", '', {
        duration: 3000
      });
      return;
    }

    // Verificar si es una actualización o creación
    if (this.recursoEdit) {
      // Llamamos al servicio para actualizar el recurso
      this.recursoService.actualizarRecurso(this.recursoEdit.recursoId, this.recurso).subscribe(
        (dato: any) => {
          Swal.fire('Recurso actualizado', 'El recurso ha sido actualizado con éxito', 'success');
          this.router.navigate(['/admin/recursos']);
        },
        (error) => {
          console.log(error);
          Swal.fire('Error !!', 'Error al actualizar el recurso', 'error');
        }
      );
    } else {
      // Llamamos al servicio para agregar el recurso
      this.recursoService.agregarRecurso(this.recurso).subscribe(
        (dato: any) => {
          this.recurso.nombre = '';
          this.recurso.descripcion = '';
          Swal.fire('Recurso agregado', 'El recurso ha sido agregado con éxito', 'success');
          this.router.navigate(['/admin/recursos']);
        },
        (error) => {
          console.log(error);
          Swal.fire('Error !!', 'Error al guardar el recurso', 'error');
        }
      );
    }
  }
}
