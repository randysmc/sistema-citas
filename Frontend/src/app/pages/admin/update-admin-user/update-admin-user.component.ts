import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EmpleadoService } from 'src/app/services/empleado.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-update-admin-user',
  templateUrl: './update-admin-user.component.html',
  styleUrls: ['./update-admin-user.component.css']
})
export class UpdateAdminUserComponent implements OnInit {

  empleadoId!: number;
  empleado: any = {
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
    this.empleadoId = this.route.snapshot.params['id'];

    this.empleadoService.obtenerEmpleadoPorId(this.empleadoId).subscribe(
      (data: any) => {
        this.empleado = data;
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
    if (this.empleado.nombre.trim() === '' || this.empleado.nombre == null) {
      Swal.fire('El nombre es requerido', '', 'warning');
      return;
    }

    const formData = new FormData();
    formData.append('empleado', JSON.stringify(this.empleado));
    if (this.selectedFile) {
      formData.append('perfil', this.selectedFile, this.selectedFile.name);
    }

    this.empleadoService.actualizarEmpleado(this.empleadoId, formData).subscribe(
      (data) => {
        Swal.fire('Empleado actualizado', 'El empleado ha sido actualizado con éxito', 'success');
        this.router.navigate(['/admin/profile']);
      },
      (error) => {
        console.log('Error al actualizar el empleado', error);
        Swal.fire('Error', 'Hubo un problema al actualizar el empleado', 'error');
      }
    );
  }
}
