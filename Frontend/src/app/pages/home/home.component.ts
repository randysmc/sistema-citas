import { Component, OnInit } from '@angular/core';
import { NegocioService } from 'src/app/services/negocio.service';
import { ServiceService } from 'src/app/services/service.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  negocio: any;
  serviciosDisponibles: any[] = [];

  constructor(private negocioService: NegocioService, private serviceService: ServiceService) {}

  ngOnInit(): void {
    this.cargarNegocio();
    this.listarServiciosDisponibles();
  }

  private cargarNegocio() {
    this.negocioService.obtenerNegocioPorId(1).subscribe(
      (data: any) => {
        this.negocio = data;
        this.negocio.fotoPerfilUrl = this.buildImageUrl(data);
      },
      error => {
        console.error('Error al obtener el negocio', error);
      }
    );
  }

  private listarServiciosDisponibles() {
    this.serviceService.obtenerServiciosDisponible().subscribe(
      (data: any) => {
        this.serviciosDisponibles = data.slice(0, 3); // Solo toma los primeros 3 servicios
      },
      error => {
        console.error('Error al cargar los servicios disponibles', error);
      }
    );
  }

  private buildImageUrl(negocio: any): string {
    const folder = 'negocio';
    const filename = negocio.fotoPerfil.split('/').pop();
    return `http://localhost:8080/uploads/${folder}/${filename}`;
  }
}
