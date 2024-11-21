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
  
  
    this.loginService.generateToken(this.loginData).subscribe(
      (response: any) => {
  
        if (response.message && response.message === 'Código de autenticación enviado. Por favor, valida el código.') {
          // Si el backend devuelve el mensaje de 2FA, redirigir al componente de TwoFaComponent
          this.router.navigate(['two-fa', { username: this.loginData.username }]);
        } else {
          // Si el backend devuelve el token, se guarda y se redirige al dashboard
          this.loginService.loginUser(response.token);
          
          // Redirigir al dashboard según el rol del usuario
          this.loginService.getCurrentUser().subscribe((user: any) => {
            this.loginService.setUser(user);
            if (this.loginService.getUserRole() === "ADMINISTRADOR") {
              this.router.navigate(['admin']);
            } else if (this.loginService.getUserRole() === "CLIENTE") {
              this.router.navigate(['cliente']);
            } else if (this.loginService.getUserRole() === "EMPLEADO"){
              this.router.navigate(['empleado']);
            } else{
              this.router.navigate(['other']);
            }
          });
        }
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
