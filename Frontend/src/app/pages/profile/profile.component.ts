import { Component } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service'; // Asegúrate de tener el servicio para manejar la autenticación
import Swal from 'sweetalert2';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  user: any = null; // Para almacenar la información del usuario
  imageUrl: string = ''; // Para almacenar la URL de la imagen
  isLoading: boolean = true; // Estado de carga

  constructor(
    private loginService: LoginService, 
    private router: Router, 
    private userService: UserService // Asegúrate de importar el servicio
  ) {}

  ngOnInit(): void {
    // Obtenemos el usuario actual
    this.loginService.getCurrentUser().subscribe(
      (user: any) => {
        this.user = user; // Almacena la información del usuario
        this.loginService.setUser(user); // Opcional: Actualiza el usuario en el localStorage
        this.imageUrl = this.buildImageUrl(user); // Construir URL de imagen
        console.log("URL de imagen:", this.imageUrl); // Verifica la URL construida
        this.isLoading = false; // Carga completa
      },
      (error) => {
        alert("Error al obtener el usuario");
        this.isLoading = false; // Carga completa incluso si hay un error
      }
    );
  }

  private buildImageUrl(user: any): string {
    const folder = 'usuario'; // Todos los usuarios en la carpeta 'usuarios'
    const filename = user.perfil.split('/').pop(); // Obtiene solo el nombre del archivo
    return `http://localhost:8080/uploads/${folder}/${filename}`; 
  }

  actualizarPerfil(): void {
    const role = this.user.authorities[0].authority;

    if (role === 'ADMINISTRADOR') {
      this.router.navigate([`/admin/update-admin`, this.user.id]);
    } else if (role === 'CLIENTE') {
      this.router.navigate([`/cliente/update-cliente`, this.user.id]);
    } else if(role === 'EMPLEADO'){
      this.router.navigate([`/empleado/update-profile`, this.user.id]);
    }
  }

  // Función para habilitar o deshabilitar la autenticación en dos pasos
  toggleTFA(): void {
    const userId = this.user.id;

    if (this.user.tfa) {
      // Si la autenticación en dos pasos está habilitada, deshabilitarla
      this.userService.deshabilitarAutenticacin(userId).subscribe(
        () => {
          this.user.tfa = false; // Cambiar el estado de tfa
          Swal.fire({
            title: 'Éxito!',
            text: 'Autenticación en dos pasos deshabilitada.',
            icon: 'success',
            confirmButtonText: 'Aceptar'
          });
        },
        (error) => {
          Swal.fire({
            title: 'Error',
            text: 'Hubo un error al deshabilitar la autenticación en dos pasos.',
            icon: 'error',
            confirmButtonText: 'Aceptar'
          });
        }
      );
    } else {
      // Si la autenticación en dos pasos está deshabilitada, habilitarla
      this.userService.habilitarAutenticacin(userId).subscribe(
        () => {
          this.user.tfa = true; // Cambiar el estado de tfa
          Swal.fire({
            title: 'Éxito!',
            text: 'Autenticación en dos pasos habilitada.',
            icon: 'success',
            confirmButtonText: 'Aceptar'
          });
        },
        (error) => {
          Swal.fire({
            title: 'Error',
            text: 'Hubo un error al habilitar la autenticación en dos pasos.',
            icon: 'error',
            confirmButtonText: 'Aceptar'
          });
        }
      );
    }
  }
}
