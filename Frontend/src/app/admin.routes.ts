import { Routes } from '@angular/router';
import { DashboardComponent } from './pages/admin/dashboard/dashboard.component'; 
import { ProfileComponent } from './pages/profile/profile.component'; 
import { CalendarComponent } from './pages/calendar/calendar.component';
import { FacturasComponent } from './pages/admin/facturas/facturas.component';
import { ViewFacturaDetailComponent } from './pages/admin/view-factura-detail/view-factura-detail.component';
import { ViewUsersComponent } from './pages/admin/Usuario/view-users/view-users.component';
import { AddUsersComponent } from './pages/admin/add-users/add-users.component';
import { ViewUserDetailComponent } from './pages/admin/Usuario/view-user-detail/view-user-detail.component';
import { UpdateAdminUserComponent } from './pages/admin/update-admin-user/update-admin-user.component';
import { WelcomeComponent } from './pages/admin/welcome/welcome.component';
import { ViewRecursosComponent } from './pages/admin/Recurso/view-recursos/view-recursos.component';
import { RecursosFormComponent } from './pages/admin/Recurso/recursos-form-component/recursos-form-component.component';
import { ViewRecursoDetailComponent } from './pages/admin/Recurso/view-recurso-detail/view-recurso-detail.component';
import { ViewServicesComponent } from './pages/admin/Servicio/view-services/view-services.component';
import { ServiciosFormComponentComponent } from './pages/admin/Servicio/servicios-form-component/servicios-form-component.component';
import { ViewServicesDetailComponent } from './pages/admin/Servicio/view-services-detail/view-services-detail.component';
import { AddDiaFestivoComponent } from './pages/admin/add-dia-festivo/add-dia-festivo.component';
import { AddHorarioLaboralComponent } from './pages/admin/add-horario-laboral/add-horario-laboral.component';
import { ViewCitasComponent } from './pages/admin/view-citas/view-citas.component';
import { ViewCitasDetailComponent } from './pages/admin/view-citas-detail/view-citas-detail.component';
import { AddEmpleadoComponent } from './pages/admin/Usuario/add-empleado/add-empleado.component';
import { ReportesComponent } from './pages/admin/reportes/reportes.component';
import { UpdateNegocioComponent } from './pages/admin/update-negocio/update-negocio.component';


export const AdminRoutes: Routes = [
    {
      path: '',
      component: DashboardComponent,
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
            path: 'add-usuario',
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
            component: RecursosFormComponent,
          },
          {
            path: 'recursos/:id',
            component: ViewRecursoDetailComponent,
          },
          {
            path: 'update-recurso/:id',
            component: RecursosFormComponent,
          },
    
          {
            path: 'servicios',
            component: ViewServicesComponent,
          },
          {
            path: 'add-servicio',
            component: ServiciosFormComponentComponent,
          },
          {
            path: 'servicio/:id',
            component: ViewServicesDetailComponent,
          },
          {
            path: 'update-servicio/:id',
            component: ServiciosFormComponentComponent,
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
            component: AddEmpleadoComponent,
          },
          {
            path: 'empleado/:id',
            component: ViewRecursoDetailComponent,
          },
          {
            path: 'reportes',
            component: ReportesComponent
          },
          {
            path: 'update-negocio/:id',
            component: UpdateNegocioComponent
          }

      ],
    },
  ];