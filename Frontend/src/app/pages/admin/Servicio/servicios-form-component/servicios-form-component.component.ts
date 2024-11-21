import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { ServiceService } from 'src/app/services/service.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-servicios-form-component',
  templateUrl: './servicios-form-component.component.html',
  styleUrls: ['./servicios-form-component.component.css']
})
export class ServiciosFormComponentComponent implements OnInit{

  servicioId: number | null = null;
  servicio = {
    nombre: '',
    descripcion: '',
    duracionServicio: '',
    precio: '',
    disponible: true,
    tipo: 'PERSONAL'
  };

  tipos = ['PERSONAL', 'INSTALACION'];

  constructor(
    private servicioService: ServiceService,
    private snack: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    
    this.servicioId = this.route.snapshot.params['id'] || null;

    if(this.servicioId){
      this.cargarServicio();
    }
  }

  cargarServicio(){
    this.servicioService.obtenerServicioPorId(this.servicioId!).subscribe(
      (data: any) => {
        this.servicio = data;
      },
      (error) => {
        Swal.fire('Error', 'Error al cargar el servicio para editar', 'error');
      }
    )
  }

  formSubmit() {
    if (this.servicio.nombre.trim() === '' || this.servicio.nombre == null) {
      this.snack.open('El nombre es requerido !!', '', { duration: 3000 });
      return;
    }

    if (this.servicioId) {
      this.servicioService.actualizarServicio(this.servicioId, this.servicio).subscribe(
        () => {
          Swal.fire('Servicio actualizado', 'El servicio ha sido actualizado con éxito', 'success');
          this.router.navigate(['/admin/servicios']);
        },
        (error) => {
          Swal.fire('Error!', 'Error al actualizar el servicio', 'error');
        }
      );
    } else {

      this.servicioService.agregarServicio(this.servicio).subscribe(
        () => {
          Swal.fire('Servicio agregado', 'El servicio ha sido agregado con éxito', 'success');
          this.router.navigate(['/admin/servicios']);
        },
        (error) => {
          Swal.fire('Error!', 'Error al guardar el servicio', 'error');
        }
      );
    }
  }

}
