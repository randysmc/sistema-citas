import { Component } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  user: any = null; // Para almacenar la información del usuario
  imageUrl: string = ''; // Para almacenar la URL de la imagen
  isLoading: boolean = true; // Estado de carga

  constructor(private loginService: LoginService, private router: Router) {}

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

    // Extraer solo el nombre del archivo de la ruta completa
    const filename = user.perfil.split('/').pop(); // Obtiene solo el nombre del archivo

    // Construir la URL usando solo el nombre del archivo
    return `http://localhost:8080/uploads/${folder}/${filename}`; 
  }


  actualizarPerfil(): void {
    const role = this.user.authorities[0].authority;

    if (role === 'ADMINISTRADOR') {
      this.router.navigate([`/admin/update-admin`, this.user.id]);
    } else if (role === 'CLIENTE') {
      this.router.navigate([`/cliente/update-cliente`, this.user.id]);
    }
  }

}
