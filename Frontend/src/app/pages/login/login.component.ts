import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginData = {
    "username" : '',
    "password" : ''
  }

  constructor(
    private snack:MatSnackBar,
    private loginService:LoginService,
    private router:Router
  ) { }

  ngOnInit():void {
    
  }

  formSubmit() {
    console.log("estoy aqui");
    if (this.loginData.username.trim() === '' || this.loginData.username.trim() == null) {
        this.snack.open('El nombre de usuario es requerido!', 'Aceptar', {
            duration: 4000
        });
        return;
    }

    if (this.loginData.password.trim() === '' || this.loginData.password.trim() == null) {
        this.snack.open('La contraseña es requerida!', 'Aceptar', {
            duration: 4000
        });
        return;
    }

    // Imprimir loginData en la consola para ver lo que se envía
    console.log('Datos a enviar:', this.loginData);

    this.loginService.generateToken(this.loginData).subscribe(
        (response: any) => {
            console.log(response); // Esto debería mostrar "Código de autenticación enviado"
            
            // Guardamos el nombre de usuario
            //localStorage.setItem('username', this.loginData.username);
            
            // Redirigimos al componente de two-fa
            this.router.navigate(['two-fa', {username: this.loginData.username}]); 
        },
        (error) => {
            console.log('Error en la autenticación:', error);
            this.snack.open('Credenciales invalidas!', 'Aceptar', {
                duration: 3000
            });
        }
    );
}

  

}
