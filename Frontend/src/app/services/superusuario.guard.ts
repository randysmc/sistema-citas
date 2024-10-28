import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from './login.service';

export const superusuarioGuard: CanActivateFn = (route, state) => {
  const loginService = inject(LoginService);
  const router = inject(Router);

  // Verifica si el usuario está logueado y si tiene el rol de SUPERUSUARIO
  if (loginService.isLoggedIn() && loginService.getUserRole() === 'SUPERUSUARIO') {
    return true; // Permitir acceso
  }

  // Redirigir al login si no cumple con los requisitos
  router.navigate(['login']);
  return false; // Bloquear acceso
};