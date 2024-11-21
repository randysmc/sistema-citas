import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { SignupComponent } from './pages/signup/signup.component';
import { LoginComponent } from './pages/login/login.component';
import { AdminGuard } from './services/admin.guard';
import { normalGuard } from './services/normal.guard';
import { TwoFaComponent } from './pages/two-fa/two-fa.component';

import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';
import { AdminRoutes } from './admin.routes';
import { ClienteRoutes } from './cliente.routes';
import { EmpleadoRoutes } from './empleado.routes';


const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full',
  },
  {
    path: 'signup',
    component: SignupComponent,
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full',
  },
  {
    path: 'two-fa',
    component: TwoFaComponent,
    pathMatch: 'full',
  },
  {
    path: 'reset-password',
    component: ResetPasswordComponent,
    pathMatch: 'full'
  },

  {
    path: 'admin',
    canActivate:[AdminGuard],
    children: AdminRoutes
  },
  {
    path: 'cliente',
    canActivate: [normalGuard],
    children: ClienteRoutes
  },
  {
    path: 'empleado',
    children: EmpleadoRoutes
  }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
