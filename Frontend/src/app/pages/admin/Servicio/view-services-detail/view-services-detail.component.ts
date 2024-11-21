import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ServiceService } from 'src/app/services/service.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-services-detail',
  templateUrl: './view-services-detail.component.html',
  styleUrls: ['./view-services-detail.component.css']
})
export class ViewServicesDetailComponent {

  servicio: any = null;
  id: number = 0;

  constructor(
    private route: ActivatedRoute,
    private servicioService: ServiceService,
    private router: Router
  ) {}

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.cargarServicio(this.id);
  }


  cargarServicio(id: number) {
    this.servicioService.obtenerServicioPorId(id).subscribe(
      (dato: any) => {
        this.servicio = dato;
      },
      (error) => {
        console.log(error);
        Swal.fire('Error', 'Error al cargar el servicio', 'error');
      }
    );
  }

  // Método para actualizar servicio (puedes agregar la lógica que necesites)
  actualizarServicio() {
    this.router.navigate([`/admin/update-servicio/${this.servicio.servicioId}`]);
  }
  
  

  // Activar servicio
  activarServicio() {
    this.servicioService.habilitarServicio(this.id).subscribe({
      next: (resp) => {
        console.log('servicio activado:', resp);
        // Actualiza el estado del servicio localmente
        this.servicio.disponible = true; 
        Swal.fire('Éxito', 'servicio activado correctamente', 'success').then(() => {
          this.cargarServicio(this.id); // Recargar el servicio
        });
      },
      error: (err) => {
        console.error('Error al activar servicio', err);
        Swal.fire('Error', 'Error al activar el servicio', 'error');
      }
    });
  }

  // Inactivar servicio
  inactivarServicio() {
    this.servicioService.deshabilitarServicio(this.id).subscribe({
      next: (resp) => {
        console.log('servicio inactivado:', resp);
        // Actualiza el estado del servicio localmente
        this.servicio.disponible = false; 
        Swal.fire('Éxito', 'servicio inactivado correctamente', 'success').then(() => {
          this.cargarServicio(this.id); // Recargar el servicio
        });
      },
      error: (err) => {
        console.error('Error al inactivar servicio', err);
        Swal.fire('Error', 'Error al inactivar el servicio', 'error');
      }
    });
  }
}
