import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NegocioService } from 'src/app/services/negocio.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {
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

  irAActualizarNegocio() {
    this.router.navigate(['admin/update-negocio', 1]); // Asegúrate de que el id de negocio esté disponible
  }



}
