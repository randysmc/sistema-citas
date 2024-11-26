import { Routes } from '@angular/router';
import { DashboardComponent } from './pages/admin/dashboard/dashboard.component'; 
import { ProfileComponent } from './pages/profile/profile.component'; 
import { CalendarComponent } from './pages/calendar/calendar.component';
import { FacturasComponent } from './pages/admin/Factura/facturas/facturas.component';
import { ViewFacturaDetailComponent } from './pages/admin/Factura/view-factura-detail/view-factura-detail.component';
import { ViewUsersComponent } from './pages/admin/crudCliente/view-users/view-users.component';
import { ViewUserDetailComponent } from './pages/admin/crudCliente/view-user-detail/view-user-detail.component';
import { UpdateAdminUserComponent } from './pages/admin/update-admin-user/update-admin-user.component';
import { WelcomeComponent } from './pages/admin/welcome/welcome.component';
import { ViewRecursosComponent } from './pages/admin/Recurso/view-recursos/view-recursos.component';
import { RecursosFormComponent } from './pages/admin/Recurso/recursos-form-component/recursos-form-component.component';
import { ViewRecursoDetailComponent } from './pages/admin/Recurso/view-recurso-detail/view-recurso-detail.component';
import { ViewServicesComponent } from './pages/admin/Servicio/view-services/view-services.component';
import { ServiciosFormComponentComponent } from './pages/admin/Servicio/servicios-form-component/servicios-form-component.component';
import { ViewServicesDetailComponent } from './pages/admin/Servicio/view-services-detail/view-services-detail.component';
import { AddDiaFestivoComponent } from './pages/admin/Dia_Festivo/add-dia-festivo/add-dia-festivo.component';
import { ViewCitasComponent } from './pages/admin/Citas/view-citas/view-citas.component';
import { ViewCitasDetailComponent } from './pages/admin/Citas/view-citas-detail/view-citas-detail.component';
import { AddEmpleadoComponent } from './pages/admin/crudEmpleado/add-empleado/add-empleado.component';
import { ReportesComponent } from './pages/admin/reportes/reportes.component';
import { UpdateNegocioComponent } from './pages/admin/Negocio/update-negocio/update-negocio.component';
import { ViewNegocioDetailComponent } from './pages/admin/Negocio/view-negocio-detail/view-negocio-detail.component';
import { HorariosFormComponent } from './pages/admin/Horario_Laboral/horarios-form/horarios-form.component';
import { ViewHorariosComponent } from './pages/admin/Horario_Laboral/view-horarios/view-horarios.component';
import { ViewHorarioDetailComponent } from './pages/admin/Horario_Laboral/view-horario-detail/view-horario-detail.component';
import { ViewEmpleadosComponent } from './pages/admin/crudEmpleado/view-empleados/view-empleados.component';
import { ViewEmpleadoDetailComponent } from './pages/admin/crudEmpleado/view-empleado-detail/view-empleado-detail.component';
import { NegocioReportesComponent } from './pages/reporte/negocio-reportes/negocio-reportes.component';


export const AdminRoutes: Routes = [
    {
      path: '',
      component: DashboardComponent,
      children: [
        {
          path: '',
          component: WelcomeComponent
        },
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
            path: 'users/:id',
            component: ViewUserDetailComponent,
          },



          {
            path: 'update-admin/:id',
            component: UpdateAdminUserComponent,
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
            path: 'horarios-laborales',
            component: ViewHorariosComponent,
          },
          {
            path: 'view-horario-laboral/:id',
            component: ViewHorarioDetailComponent
          },
          {
            path: 'add-horario-laboral',
            component: HorariosFormComponent,
          },
          {
            path: 'update-horario-laboral/:id',
            component:HorariosFormComponent
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
            component: ViewEmpleadosComponent
          },
          {
            path: 'add-empleado',
            component: AddEmpleadoComponent,
          },
          {
            path: 'empleado/:id',
            component: ViewEmpleadoDetailComponent,
          },


          

          {
            path: 'reportes',
            component: NegocioReportesComponent
          },
          {
            path: 'update-negocio/:id',
            component: UpdateNegocioComponent
          },
          {
            path: 'view-negocio',
            component: ViewNegocioDetailComponent
          }

      ],
    },
  ];