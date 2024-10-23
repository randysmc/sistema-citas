import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NegocioService } from 'src/app/services/negocio.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-negocios',
  templateUrl: './view-negocios.component.html',
  styleUrls: ['./view-negocios.component.css']
})
export class ViewNegociosComponent {
  negocios: any = [];

  constructor(private negocioService: NegocioService, private router:Router) {}

  ngOnInit(): void {
    // Llamar al servicio para listar los negocios
    this.negocioService.listarNegocios().subscribe(
      (data: any) => {
        this.negocios = data.map((negocio: any) => {
          // Construimos la URL de la imagen para cada negocio
          negocio.fotoPerfil = this.buildImageUrl(negocio);
          return negocio;
        });
        console.log(this.negocios);
      },
      (error) => {
        console.log(error);
        Swal.fire('Error!', 'Error al cargar los negocios', 'error');
      }
    );
  }

  // Construir la URL de la imagen del negocio
  private buildImageUrl(negocio: any): string {
    const folder = 'negocio'; // Carpeta de imágenes de negocios

    if (negocio.fotoPerfil) {
      const filename = negocio.fotoPerfil.split('/').pop(); // Extrae solo el nombre del archivo
      return `http://localhost:8080/uploads/${folder}/${filename}`; // URL completa de la imagen
    }
    
    // Si no tiene fotoPerfil, retorna una imagen por defecto
    return 'assets/default.jpg';
  }

  verNegocio(id: number) {
    this.router.navigate([`superusuario/negocios/${id}`]); // Navega con el prefijo adecuado
  }
  
  
}
