import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

//importaciones de angular material
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input'; 
import {MatSnackBarModule} from '@angular/material/snack-bar'; 
import {MatCardModule} from '@angular/material/card'; 
import {MatToolbarModule} from '@angular/material/toolbar'; 
import {MatIconModule} from '@angular/material/icon'; 
import {MatListModule} from '@angular/material/list';
import { ReactiveFormsModule } from '@angular/forms';
import {MatSelectModule} from '@angular/material/select';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { MatTableModule } from '@angular/material/table';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';


import { NavbarComponent } from './components/navbar/navbar.component';
import { SignupComponent } from './pages/signup/signup.component';
import { LoginComponent } from './pages/login/login.component'; 
import { FormsModule } from '@angular/forms';
import { HomeComponent } from './pages/home/home.component';
import { authInterceptorProviders } from './services/auth.interceptors';
import { DashboardComponent } from './pages/admin/dashboard/dashboard.component';
import { TwoFaComponent } from './pages/two-fa/two-fa.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { SidebarComponent } from './pages/admin/sidebar/sidebar.component';
import { WelcomeComponent } from './pages/admin/welcome/welcome.component';
import { ViewRecursosComponent } from './pages/admin/Recurso/view-recursos/view-recursos.component';
import { ViewRecursoDetailComponent } from './pages/admin/Recurso/view-recurso-detail/view-recurso-detail.component';
import { CalendarComponent } from './pages/calendar/calendar.component';
import { FullCalendarModule } from '@fullcalendar/angular';
import { ViewUserDetailComponent } from './pages/admin/crudCliente/view-user-detail/view-user-detail.component';
import { ViewUsersComponent } from './pages/admin/crudCliente/view-users/view-users.component';
import { AddEmpleadoComponent } from './pages/admin/crudEmpleado/add-empleado/add-empleado.component';
import { ClienteDashboardComponent } from './pages/cliente/cliente-dashboard/cliente-dashboard.component';
import { SidebarClienteComponent } from './pages/cliente/sidebar-cliente/sidebar-cliente.component';
import { UpdateAdminUserComponent } from './pages/admin/update-admin-user/update-admin-user.component';
import { UpdateClienteUserComponent } from './pages/cliente/update-cliente-user/update-cliente-user.component';
import { ViewServicesComponent } from './pages/admin/Servicio/view-services/view-services.component';
import { ViewServicesDetailComponent } from './pages/admin/Servicio/view-services-detail/view-services-detail.component';
import { AddDiaFestivoComponent } from './pages/admin/Dia_Festivo/add-dia-festivo/add-dia-festivo.component';
import { UpdateNegocioComponent } from './pages/admin/Negocio/update-negocio/update-negocio.component';
import { ViewCitasComponent } from './pages/admin/Citas/view-citas/view-citas.component';
import { ViewCitasDetailComponent } from './pages/admin/Citas/view-citas/view-citas-detail/view-citas-detail.component';
import { AddClienteCitaComponent } from './pages/cliente/Citas/add-cliente-cita/add-cliente-cita.component';
import { MyChartComponent } from './pages/admin/my-chart/my-chart.component';
import { UserViewServicesComponent } from './pages/cliente/Servicio/user-view-services/user-view-services.component';
import { UserViewServicesDetailComponent } from './pages/cliente/Servicio/user-view-services-detail/user-view-services-detail.component';
import { FacturasComponent } from './pages/admin/Factura/facturas/facturas.component';
import { ViewFacturaDetailComponent } from './pages/admin/Factura/view-factura-detail/view-factura-detail.component';
import { UserViewFacturasComponent } from './pages/cliente/Factura/user-view-facturas/user-view-facturas.component';
import { UserViewCitasComponent } from './pages/cliente/Citas/user-view-citas/user-view-citas.component';
import { UserViewCitasDetailComponent } from './pages/cliente/Citas/user-view-citas-detail/user-view-citas-detail.component';
import { UserViewFacturasDetailComponent } from './pages/cliente/Factura/user-view-facturas-detail/user-view-facturas-detail.component';
import { ReportesComponent } from './pages/admin/reportes/reportes.component';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';
import { RecursosFormComponent } from './pages/admin/Recurso/recursos-form-component/recursos-form-component.component';
import { ServiciosFormComponentComponent } from './pages/admin/Servicio/servicios-form-component/servicios-form-component.component';
import { EmpleadoDashboardComponent } from './pages/empleado/empleado-dashboard/empleado-dashboard.component';
import { SidebarEmpleadoComponent } from './pages/empleado/sidebar-empleado/sidebar-empleado.component';
import { ViewCitasEmpleadoComponent } from './pages/empleado/citas/view-citas-empleado/view-citas-empleado.component';
import { ViewCitasDetailEmpleadoComponent } from './pages/empleado/citas/view-citas-detail-empleado/view-citas-detail-empleado.component';
import { EmpleadoUpdateProfileComponent } from './pages/empleado/empleado-update-profile/empleado-update-profile.component';
import { ViewNegocioDetailComponent } from './pages/admin/Negocio/view-negocio-detail/view-negocio-detail.component';
import { FooterComponent } from './components/footer/footer.component';
import { HorariosFormComponent } from './pages/admin/Horario_Laboral/horarios-form/horarios-form.component';
import { ViewHorariosComponent } from './pages/admin/Horario_Laboral/view-horarios/view-horarios.component';
import { ViewHorarioDetailComponent } from './pages/admin/Horario_Laboral/view-horario-detail/view-horario-detail.component';
import { ViewEmpleadoDetailComponent } from './pages/admin/crudEmpleado/view-empleado-detail/view-empleado-detail.component';
import { ViewEmpleadosComponent } from './pages/admin/crudEmpleado/view-empleados/view-empleados.component';






@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SignupComponent,
    LoginComponent,
    HomeComponent,
    DashboardComponent,
    TwoFaComponent,
    ProfileComponent,
    SidebarComponent,
    WelcomeComponent,
    ViewRecursosComponent,
    ViewRecursoDetailComponent,
    CalendarComponent,
    ViewUserDetailComponent,
    ViewUsersComponent,
    AddEmpleadoComponent,
    ClienteDashboardComponent,
    SidebarClienteComponent,
    UpdateAdminUserComponent,
    UpdateClienteUserComponent,
    ViewServicesComponent,
    ViewServicesDetailComponent,
    AddDiaFestivoComponent,
    UpdateNegocioComponent,
    ViewCitasComponent,
    ViewCitasDetailComponent,
    AddClienteCitaComponent,
    MyChartComponent,
    UserViewServicesComponent,
    UserViewServicesDetailComponent,
    FacturasComponent,
    ViewFacturaDetailComponent,
    UserViewFacturasComponent,
    UserViewCitasComponent,
    UserViewCitasDetailComponent,
    UserViewFacturasDetailComponent,
    ReportesComponent,
    ResetPasswordComponent,
    RecursosFormComponent,
    ServiciosFormComponentComponent,
    EmpleadoDashboardComponent,
    SidebarEmpleadoComponent,
    ViewCitasEmpleadoComponent,
    ViewCitasDetailEmpleadoComponent,
    EmpleadoUpdateProfileComponent,
    ViewNegocioDetailComponent,
    FooterComponent,
    HorariosFormComponent,
    ViewHorariosComponent,
    ViewHorarioDetailComponent,
    ViewEmpleadoDetailComponent,
    ViewEmpleadosComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule, 
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    FormsModule,
    MatSnackBarModule,
    MatCardModule,
    MatToolbarModule,
    MatIconModule,
    MatListModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatCheckboxModule,
    FullCalendarModule,
    MatTableModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
