import { Component } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  user: any = null; // Para almacenar la información del usuario

  constructor(
    private loginService: LoginService
  ) {}

  ngOnInit(): void {
    // Obtenemos el usuario actual
    this.loginService.getCurrentUser().subscribe(
      (user: any) => {
        this.user = user; // Almacena la información del usuario
        this.loginService.setUser(user); // Opcional: Actualiza el usuario en el localStorage
      },
      (error) => {
        alert("Error al obtener el usuario");
      }
    );
  }

}
