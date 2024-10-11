import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-two-fa',
  templateUrl: './two-fa.component.html',
  styleUrls: ['./two-fa.component.css']
})

export class TwoFaComponent {
  twoFactorCode: string = '';
  username: string = '';

  constructor(private route: ActivatedRoute, private loginService: LoginService, private router: Router, private snack: MatSnackBar){
    //Recuperamos el usario
    this.username = localStorage.getItem('username') || '';

  }

  ngOnInit():void{
    this.route.paramMap.subscribe(params => {
      this.username = params.get('username') || '';
    });
  }

  validateTwoFactorCode() {
    // Validar que el código de verificación no esté vacío
    if (this.twoFactorCode.trim() === '' || this.twoFactorCode == null) {
      this.snack.open('El código 2FA es requerido', 'Aceptar', {
        duration: 3000
      });
      return;
    }
  
    // Crear el objeto con el nombre de usuario y el código de verificación
    const twoFactorData = { 
      username: this.username,
      twoFactorCode: this.twoFactorCode
    };
  
    // Realizar la solicitud para validar el código de verificación
    this.loginService.validateTwoFactorCode(twoFactorData).subscribe(
      (response: any) => {
        // Si la respuesta es correcta, se maneja el token
        this.loginService.loginUser(response.token);
  
        // Aquí puedes obtener los datos del usuario, si es necesario
        this.loginService.getCurrentUser().subscribe((user: any) => {
          this.loginService.setUser(user);
          console.log(user);
  
          // Navegar según el rol del usuario
          if (this.loginService.getUserRole() === "ADMIN") {
            this.router.navigate(['admin']);
            this.loginService.loginStatusSubjec.next(true);
          } else if (this.loginService.getUserRole() === "NORMAL") {
            this.router.navigate(['user-dashboard']);
            this.loginService.loginStatusSubjec.next(true);
          } else {
            this.loginService.logout();
          }
        });
      },
      (error) => {
        console.log(error);
        this.snack.open('Código de verificación inválido, vuelva a intentar!', 'Aceptar', {
          duration: 3000
        });
      }
    );
  }
  

}
