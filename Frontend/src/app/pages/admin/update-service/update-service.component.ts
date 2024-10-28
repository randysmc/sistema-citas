import { Component } from '@angular/core';
import { ServiceService } from 'src/app/services/service.service';
import Swal from 'sweetalert2';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-update-service',
  templateUrl: './update-service.component.html',
  styleUrls: ['./update-service.component.css']
})
export class UpdateServiceComponent {
  servicioId!:number;
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
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    // Obtener el ID del servicio desde la ruta
    this.servicioId = this.route.snapshot.params['id'];

    // Cargar el servicio existente para actualizarlo
    this.servicioService.obtenerServicioPorId(this.servicioId).subscribe(
      (data: any) => {
        this.servicio = data;  // Cargar los detalles del servicio
      },
      (error) => {
        console.log('Error al cargar el servicio', error);
      }
    );
  }

  formSubmit() {
    if (this.servicio.nombre.trim() === '' || this.servicio.nombre == null) {
      Swal.fire('El título es requerido', '', 'warning');
      return;
    }
  
    // Llamada al servicio para actualizar el servicio
    this.servicioService.actualizarServicio(this.servicioId, this.servicio).subscribe(
      (data) => {
        Swal.fire('Servicio actualizado', 'El servicio ha sido actualizado con éxito', 'success');
        this.router.navigate(['/admin/servicios']);
      },
      (error) => {
        console.log('Error al actualizar el servicio', error);
        
        // Muestra el mensaje de error específico del backend
        const errorMessage = error.error?.message || 'Hubo un problema al actualizar el servicio';
        const errorDetails = error.error?.details || '';
  
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: errorMessage,
          footer: errorDetails ? `<p>${errorDetails}</p>` : ''
        });
      }
    );
  }
  

}
