import { Component, Input, OnInit } from '@angular/core';
import { ServiceService } from 'src/app/services/service.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-service',
  templateUrl: './add-service.component.html',
  styleUrls: ['./add-service.component.css']
})
export class AddServiceComponent {

  @Input() servicioEdit: any; // Para recibir el recurso a editar
  servicio = {
    nombre: '',
    descripcion: '',
    duracionServicio: '',
    precio: '',
    disponible: true,
  };

  constructor(
    private servicioService: ServiceService,
    private snack: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Si existe un recurso para editar, carga sus datos
    if (this.servicioEdit) {
      this.servicio = {
        ...this.servicioEdit
      };
    }
  }

  formSubmit() {
    if (this.servicio.nombre.trim() === '' || this.servicio.nombre == null) {
      this.snack.open("El título es requerido !!", '', {
        duration: 3000
      });
      return;
    }

    // Verificar si es una actualización o creación
    if (this.servicioEdit) {
      // Llamamos al servicio para actualizar el recurso
      this.servicioService.actualizarServicio(this.servicioEdit.sercvicioId, this.servicio).subscribe(
        (dato: any) => {
          Swal.fire('Servicio actualizado', 'El recurso ha sido actualizado con éxito', 'success');
          this.router.navigate(['/admin/servicios']);
        },
        (error) => {
          console.log(error);
          Swal.fire('Error !!', 'Error al actualizar el recurso', 'error');
        }
      );
    } else {
      // Llamamos al servicio para agregar el recurso
      this.servicioService.agregarServicio(this.servicio).subscribe(
        (dato: any) => {
          this.servicio.nombre = '';
          this.servicio.descripcion = '';
          this.servicio.precio = '';
          this.servicio.duracionServicio= '';
          Swal.fire('Servicio agregado', 'El servicio ha sido agregado con éxito', 'success');
          this.router.navigate(['/admin/servicios']);
        },
        (error) => {
          console.log(error);
          Swal.fire('Error !!', 'Error al guardar el servicio', 'error');
        }
      );
    }
  }

}
