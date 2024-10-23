import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NegocioService } from 'src/app/services/negocio.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-detalle-negocio',
  templateUrl: './detalle-negocio.component.html',
  styleUrls: ['./detalle-negocio.component.css']
})
export class DetalleNegocioComponent implements OnInit {

  negocio: any;

  constructor(
    private negocioService: NegocioService,
    private route: ActivatedRoute,
    private router: Router // Inyectamos Router para navegación
  ) {}

  ngOnInit(): void {
    // Obtener el id de negocio desde la URL
    const id = this.route.snapshot.params['id'];

    // Llamar al servicio para obtener los detalles de negocio por Id
    this.negocioService.obtenerNegocioPorId(id).subscribe(
      (data: any) => {
        this.negocio = data;
        // Asignar la URL de la imagen con el método buildImageUrl
        this.negocio.fotoPerfil = this.buildImageUrl(this.negocio);
      },
      (error) => {
        console.log(error);
        Swal.fire('Error', 'Error al cargar el negocio', 'error');
      }
    );
  }

  // Método para construir la URL de la imagen del negocio
  private buildImageUrl(negocio: any): string {
    const folder = 'negocio'; // Carpeta donde están almacenadas las imágenes
    if (negocio.fotoPerfil) {
      const filename = negocio.fotoPerfil.split('/').pop(); // Extraer solo el nombre del archivo
      return `http://localhost:8080/uploads/${folder}/${filename}`; // Retorna la URL completa
    }
    return 'assets/default.jpg'; // Imagen por defecto si no hay fotoPerfil
  }

  // Método para navegar a la página de actualización
  actualizarNegocio() {
    this.router.navigate([`/superusuario/update-negocio/${this.negocio.id}`]); // Navegar a la página de actualización
  }
}
