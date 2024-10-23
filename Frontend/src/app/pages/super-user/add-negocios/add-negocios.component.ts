import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NegocioService } from 'src/app/services/negocio.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-negocios',
  templateUrl: './add-negocios.component.html',
  styleUrls: ['./add-negocios.component.css']
})
export class AddNegociosComponent {
  negocio = {
    nombre: '',
    direccion: '',
    descripcion: '',
    telefono: '',
    fotoPerfil: ''
  };

  selectedFile: File | null = null;

  constructor(private negocioService: NegocioService) {}

  onFileChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  onSubmit() {
    if (this.negocio.nombre && this.negocio.direccion && this.negocio.descripcion && this.negocio.telefono) {
      const formData = new FormData();
      formData.append('nombre', this.negocio.nombre);
      formData.append('direccion', this.negocio.direccion);
      formData.append('descripcion', this.negocio.descripcion);
      formData.append('telefono', this.negocio.telefono);

      if (this.selectedFile) {
        formData.append('fotoPerfil', this.selectedFile, this.selectedFile.name);
      }

      this.negocioService.agregarNegocio(formData).subscribe(
        (response: any) => {
          Swal.fire('Negocio agregado', 'El negocio ha sido agregado con éxito', 'success');
        },
        (error: any) => {
          Swal.fire('Error', 'Hubo un problema al agregar el negocio', 'error');
        }
      );
    }
  }
}
