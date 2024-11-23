import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NegocioService } from 'src/app/services/negocio.service';

@Component({
  selector: 'app-view-negocio-detail',
  templateUrl: './view-negocio-detail.component.html',
  styleUrls: ['./view-negocio-detail.component.css']
})

export class ViewNegocioDetailComponent implements OnInit{

  negocio: any;

  constructor(private negocioService: NegocioService, private router: Router) {}


  ngOnInit(): void {
    this.cargarNegocio();
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

  private buildImageUrl(negocio: any): string {
    const folder = 'negocio';
    const filename = negocio.fotoPerfil.split('/').pop();
    return `http://localhost:8080/uploads/${folder}/${filename}`;
  }

  cambiarEstadoCitasAleatorias() {
    const id = 1
    if (this.negocio.citasAleatorias) {
      this.negocioService.deshabilitarCitasAleatorias(id).subscribe(
        (response: any) => {
          console.log(response.message);
          this.negocio.citasAleatorias = false;
        },
        (error) => {
          console.error('Error al deshabilitar citas aleatorias', error);
        }
      );
    } else {
      this.negocioService.habilitarCitasAleatorias(id).subscribe(
        (response: any) => {
          console.log(response.message);
          this.negocio.citasAleatorias = true;
        },
        (error) => {
          console.error('Error al habilitar citas aleatorias', error);
        }
      );
    }
  }

  irAActualizarNegocio() {
    this.router.navigate(['admin/update-negocio', 1]); // Asegúrate de que el id de negocio esté disponible
  }
}