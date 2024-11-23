import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EmpleadoService } from 'src/app/services/empleado.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-empleado-update-profile',
  templateUrl: './empleado-update-profile.component.html',
  styleUrls: ['./empleado-update-profile.component.css']
})
export class EmpleadoUpdateProfileComponent {
  usuarioId!: number;
  usuario: any = {
    username: '',
    nombre: '',
    apellido: '',
    email: '',
    telefono: '',
    nit: '',
    cui: '',
    perfil: ''
  };
  selectedFile!: File; // Para almacenar el archivo de la imagen

  constructor(
    private route: ActivatedRoute,
    private empleadoService: EmpleadoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.usuarioId = this.route.snapshot.params['id'];

    this.empleadoService.obtenerEmpleadoPorId(this.usuarioId).subscribe(
      (data: any) => {
        this.usuario = data;
      },
      (error) => {
        console.log('Error al cargar el empleado', error);
      }
    );
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  formSubmit() {
    if (this.usuario.nombre.trim() === '' || this.usuario.nombre == null) {
      Swal.fire('El nombre es requerido', '', 'warning');
      return;
    }

    const formData = new FormData();
    formData.append('empleado', JSON.stringify(this.usuario));
    if (this.selectedFile) {
      formData.append('perfil', this.selectedFile, this.selectedFile.name);
    }

    this.empleadoService.actualizarEmpleado(this.usuarioId, formData).subscribe(
      (data) => {
        Swal.fire('Empleado actualizado', 'El empleado ha sido actualizado con éxito', 'success');
        this.router.navigate(['/cliente/profile']);
      },
      (error) => {
        console.log('Error al actualizar el empleado', error);
        Swal.fire('Error', 'Hubo un problema al actualizar el empleado', 'error');
      }
    );
  }
}
