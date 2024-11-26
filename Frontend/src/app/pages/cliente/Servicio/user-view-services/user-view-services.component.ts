import { Component } from '@angular/core';
import { ServiceService } from 'src/app/services/service.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-user-view-services',
  templateUrl: './user-view-services.component.html',
  styleUrls: ['./user-view-services.component.css']
})
export class UserViewServicesComponent {

  servicios: any = [];
  serviciosDisponibles: any = [];
  serviciosNoDisponibles: any = [];
  filtroSeleccionado: string = 'todos'; // valor inicial del filtro

  constructor(private servicioService: ServiceService, private router: Router) {}

  ngOnInit(): void {
    this.listarServiciosDisponibles();
  }



  listarServiciosDisponibles() {
    this.servicioService.obtenerServiciosDisponible().subscribe(
      (dato: any) => {
        this.serviciosDisponibles = dato;
      },
      (error) => {
        Swal.fire('Error!', 'Error al cargar los servicios disponibles', 'error');
      }
    );
  }



  verDetalle(id: number) {
    this.router.navigate(['/cliente/servicio', id]);
  }

}
