import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { SignupComponent } from './pages/signup/signup.component';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/admin/dashboard/dashboard.component';
import { UserDashboardComponent } from './pages/user/user-dashboard/user-dashboard.component';
import { AdminGuard } from './services/admin.guard';
import { normalGuard } from './services/normal.guard';
import { TwoFaComponent } from './pages/two-fa/two-fa.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { WelcomeComponent } from './pages/admin/welcome/welcome.component';
import { ViewRecursosComponent } from './pages/admin/view-recursos/view-recursos.component';
import { AddRecursosComponent } from './pages/admin/add-recursos/add-recursos.component';
import { ViewRecursoDetailComponent } from './pages/admin/view-recurso-detail/view-recurso-detail.component';
import { UpdateRecursoComponent } from './pages/admin/update-recurso/update-recurso.component';
import { CalendarComponent } from './pages/calendar/calendar.component';
import { ViewUserDetailComponent } from './pages/admin/view-user-detail/view-user-detail.component';
import { ViewUsersComponent } from './pages/admin/view-users/view-users.component';
import { AddUsersComponent } from './pages/admin/add-users/add-users.component';
import { ClienteDashboardComponent } from './pages/cliente/cliente-dashboard/cliente-dashboard.component';
import { UpdateAdminUserComponent } from './pages/admin/update-admin-user/update-admin-user.component';
import { UpdateClienteUserComponent } from './pages/cliente/update-cliente-user/update-cliente-user.component';
import { ViewServicesComponent } from './pages/admin/view-services/view-services.component';
import { AddServiceComponent } from './pages/admin/add-service/add-service.component';
import { ViewServicesDetailComponent } from './pages/admin/view-services-detail/view-services-detail.component';
import { UpdateServiceComponent } from './pages/admin/update-service/update-service.component';
import { AddDiaFestivoComponent } from './pages/admin/add-dia-festivo/add-dia-festivo.component';
import { AddHorarioLaboralComponent } from './pages/admin/add-horario-laboral/add-horario-laboral.component';
import { ViewCitasComponent } from './pages/admin/view-citas/view-citas.component';
import { ViewCitasDetailComponent } from './pages/admin/view-citas-detail/view-citas-detail.component';
import { AddClienteCitaComponent } from './pages/cliente/add-cliente-cita/add-cliente-cita.component';
import { UserViewServicesComponent } from './pages/cliente/user-view-services/user-view-services.component';
import { UserViewServicesDetailComponent } from './pages/cliente/user-view-services-detail/user-view-services-detail.component';
import { FacturasComponent } from './pages/admin/facturas/facturas.component';
import { ViewFacturaDetailComponent } from './pages/admin/view-factura-detail/view-factura-detail.component';
import { UserViewFacturasComponent } from './pages/cliente/user-view-facturas/user-view-facturas.component';
import { UserViewFacturasDetailComponent } from './pages/cliente/user-view-facturas-detail/user-view-facturas-detail.component';
import { UserViewCitasComponent } from './pages/cliente/user-view-citas/user-view-citas.component';
import { UserViewCitasDetailComponent } from './pages/cliente/user-view-citas-detail/user-view-citas-detail.component';
import { ReportesComponent } from './pages/admin/reportes/reportes.component';

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
    path: 'admin',
    component: DashboardComponent,
    canActivate:[AdminGuard],
    children: [
      {
        path: 'profile',
        component: ProfileComponent,
      },
      {
        path: 'calendario',
        component: CalendarComponent,
        //canActivate: [normalGuard, AdminGuard, superusuarioGuard], // Si quieres que sea accesible solo si están logueados, usando tus guards
      },
      {
        path: 'facturas',
        component: FacturasComponent,
      },
      {
        path: 'facturas/:id',
        component: ViewFacturaDetailComponent,
      },
      {
        path: 'users',
        component: ViewUsersComponent,
      },
      {
        path: 'add-user',
        component: AddUsersComponent,
      },
      {
        path: 'users/:id',
        component: ViewUserDetailComponent,
      },
      {
        path: 'update-admin/:id',
        component: UpdateAdminUserComponent,
      },

      {
        path: '',
        component: WelcomeComponent,
      },
      {
        path: 'recursos',
        component: ViewRecursosComponent,
      },
      {
        path: 'add-recurso',
        component: AddRecursosComponent,
      },
      {
        path: 'recursos/:id',
        component: ViewRecursoDetailComponent,
      },
      {
        path: 'update-recurso/:id',
        component: UpdateRecursoComponent,
      },

      {
        path: 'servicios',
        component: ViewServicesComponent,
      },
      {
        path: 'add-servicio',
        component: AddServiceComponent,
      },
      {
        path: 'servicio/:id',
        component: ViewServicesDetailComponent,
      },
      {
        path: 'update-servicio/:id',
        component: UpdateServiceComponent,
      },
      {
        path: 'add-dia-festivo',
        component: AddDiaFestivoComponent,
      },
      {
        path: 'add-horario-laboral',
        component: AddHorarioLaboralComponent,
      },
      {
        path: 'citas',
        component: ViewCitasComponent,
      },
      {
        path: 'citas/:id',
        component: ViewCitasDetailComponent,
      },

      {
        path: 'empleados',
        component: ViewRecursosComponent,
      },
      {
        path: 'add-empleado',
        component: AddRecursosComponent,
      },
      {
        path: 'empleado/:id',
        component: ViewRecursoDetailComponent,
      },
      {
        path: 'reportes',
        component: ReportesComponent
      }
    ],
  },
  {
    path: 'cliente',
    component: ClienteDashboardComponent,
    canActivate: [normalGuard],
    children: [
      {
        path: 'profile',
        component: ProfileComponent,
      },
      {
        path: 'update-cliente/:id',
        component: UpdateClienteUserComponent,
      },
      {
        path: 'add-cita',
        component: AddClienteCitaComponent,
      },
      {
        path: 'servicios',
        component: UserViewServicesComponent,
      },
      {
        path: 'servicio/:id',
        component: UserViewServicesDetailComponent,
      },
      {
        path: 'servicios',
        component: ViewServicesComponent,
      },
      {
        path: 'facturas',
        component: UserViewFacturasComponent,
      },
      {
        path: 'facturas/:id',
        component: UserViewFacturasDetailComponent,
      },
      {
        path: 'citas',
        component: UserViewCitasComponent,
      },
      {
        path: 'citas/:id',
        component: UserViewCitasDetailComponent,
      },

      {
        path: '',
        component: WelcomeComponent,
      },
    ],
  },

  {
    path: 'user-dashboard',
    component: UserDashboardComponent,
    pathMatch: 'full',
    //canActivate: [normalGuard]
  },
  {
    path: 'two-fa',
    component: TwoFaComponent,
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
