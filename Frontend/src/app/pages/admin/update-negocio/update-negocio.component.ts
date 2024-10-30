import { Component, OnInit } from '@angular/core';
import { NegocioService } from 'src/app/services/negocio.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-negocio',
  templateUrl: './update-negocio.component.html',
  styleUrls: ['./update-negocio.component.css']
})
export class UpdateNegocioComponent implements OnInit {
  negocio: any = {};
  selectedFile: File | null = null;

  constructor(
    private negocioService: NegocioService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id =1 // Obtener el id de los parámetros de la ruta
    this.cargarNegocio(id);
  }

  private cargarNegocio(id: number) {
    this.negocioService.obtenerNegocioPorId(id).subscribe(
      (data: any) => {
        this.negocio = data;
      },
      error => {
        console.error('Error al obtener el negocio', error);
      }
    );
  }

  actualizarNegocio() {
    this.negocioService.actualizarNegocio(this.negocio.id, this.negocio).subscribe(
      () => {
        // Redirige después de la actualización
        this.router.navigate(['/welcome']);
      },
      error => {
        console.error('Error al actualizar el negocio', error);
      }
    );
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  formSubmit() {
    // Aquí puedes crear la lógica para actualizar el negocio
    const formData = new FormData();
    formData.append('nombre', this.negocio.nombre);
    formData.append('direccion', this.negocio.direccion);
    formData.append('descripcion', this.negocio.descripcion);
    formData.append('telefono', this.negocio.telefono);
    formData.append('email', this.negocio.email);
    formData.append('slogan', this.negocio.slogan);
    if (this.selectedFile) {
      formData.append('fotoPerfil', this.selectedFile, this.selectedFile.name);
    }

    const id = +this.route.snapshot.paramMap.get('id')!;
    this.negocioService.actualizarNegocio(id, formData).subscribe(
      response => {
        console.log('Negocio actualizado', response);
        this.router.navigate(['/admin']); // Redirige a una ruta de éxito después de la actualización
      },
      error => {
        console.error('Error al actualizar el negocio', error);
      }
    );
  }
}
