import { Component } from '@angular/core';
import { ServiceService } from 'src/app/services/service.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-services',
  templateUrl: './view-services.component.html',
  styleUrls: ['./view-services.component.css']
})
export class ViewServicesComponent {

  servicios: any = [];
  serviciosDisponibles: any = [];
  serviciosNoDisponibles: any = [];
  filtroSeleccionado: string = 'todos'; // valor inicial del filtro

  constructor(private servicioService: ServiceService, private router: Router) {}

  ngOnInit(): void {
    this.listarTodosServicios();
    this.listarServiciosDisponibles();
    this.listarServiciosNoDisponibles();
  }

  listarTodosServicios() {
    this.servicioService.listarServicios().subscribe(
      (dato: any) => {
        this.servicios = dato;
      },
      (error) => {
        Swal.fire('Error!', 'Error al cargar los servicios', 'error');
      }
    );
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

  listarServiciosNoDisponibles() {
    this.servicioService.obtenerServiciosNoDisponibles().subscribe(
      (dato: any) => {
        this.serviciosNoDisponibles = dato;
      },
      (error) => {
        Swal.fire('Error!', 'Error al cargar los servicios no disponibles', 'error');
      }
    );
  }

  verDetalle(id: number) {
    this.router.navigate(['/admin/servicio', id]);
  }
}
