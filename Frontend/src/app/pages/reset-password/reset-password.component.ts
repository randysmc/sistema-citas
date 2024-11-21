// Componente reset-password.component.ts
import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})

export class ResetPasswordComponent {
  email: string = '';
  resetCode: string = '';
  newPassword: string = '';
  step: number = 1; // 1: Ingresar correo, 2: Código y nueva contraseña

  constructor(
    private loginService: LoginService,
    private snack: MatSnackBar,
    private router: Router
  ) {}

  // Primer paso: Validar el correo
  submitEmail() {
    if (this.email.trim() === '') {
      this.snack.open('El correo es requerido!', 'Aceptar', {
        duration: 4000,
      });
      return;
    }

    this.loginService.forgotPassword(this.email).subscribe(
      (response: any) => {
        this.snack.open('Correo validado. Ingresa el código de recuperación.', 'Aceptar', {
          duration: 4000,
        });
        this.step = 2; // Avanzar al paso 2
      },
      (error) => {
        this.snack.open(
          error.status === 404
            ? 'Usuario no encontrado con el correo proporcionado.'
            : 'Ocurrió un error al enviar el correo.',
          'Aceptar',
          {
            duration: 4000,
          }
        );
      }
    );
  }

  // Segundo paso: Validar código y establecer nueva contraseña
  resetPassword() {
    if (!this.resetCode.trim() || !this.newPassword.trim()) {
      this.snack.open('El código y la nueva contraseña son requeridos!', 'Aceptar', {
        duration: 4000,
      });
      return;
    }

    const data = {
      email: this.email,
      resetCode: this.resetCode,
      newPassword: this.newPassword,
    };

    this.loginService.resetPassword(data).subscribe(
      (response: any) => {
        this.snack.open('Contraseña cambiada exitosamente.', 'Aceptar', {
          duration: 4000,
        });
        this.router.navigate(['/login'])
      },
      (error) => {
        this.snack.open(
          error.status === 400
            ? 'Código de recuperación inválido o expirado.'
            : 'Ocurrió un error al cambiar la contraseña.',
          'Aceptar',
          {
            duration: 4000,
          }
        );
      }
    );
  }
}