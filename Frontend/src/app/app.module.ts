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
import { UserDashboardComponent } from './pages/user/user-dashboard/user-dashboard.component';
import { TwoFaComponent } from './pages/two-fa/two-fa.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { SidebarComponent } from './pages/admin/sidebar/sidebar.component';
import { WelcomeComponent } from './pages/admin/welcome/welcome.component';
import { ViewRecursosComponent } from './pages/admin/view-recursos/view-recursos.component';
import { AddRecursosComponent } from './pages/admin/add-recursos/add-recursos.component';
import { ViewRecursoDetailComponent } from './pages/admin/view-recurso-detail/view-recurso-detail.component';
import { UpdateRecursoComponent } from './pages/admin/update-recurso/update-recurso.component';
import { CalendarComponent } from './pages/calendar/calendar.component';
import { FullCalendarModule } from '@fullcalendar/angular';
import { AddUsersComponent } from './pages/admin/add-users/add-users.component';
import { ViewUserDetailComponent } from './pages/admin/view-user-detail/view-user-detail.component';
import { ViewUsersComponent } from './pages/admin/view-users/view-users.component';
import { AddEmpleadoComponent } from './pages/admin/add-empleado/add-empleado.component';
import { ClienteDashboardComponent } from './pages/cliente/cliente-dashboard/cliente-dashboard.component';
import { SidebarClienteComponent } from './pages/cliente/sidebar-cliente/sidebar-cliente.component';
import { UpdateAdminUserComponent } from './pages/admin/update-admin-user/update-admin-user.component';
import { UpdateClienteUserComponent } from './pages/cliente/update-cliente-user/update-cliente-user.component';
import { ViewServicesComponent } from './pages/admin/view-services/view-services.component';
import { ViewServicesDetailComponent } from './pages/admin/view-services-detail/view-services-detail.component';
import { AddServiceComponent } from './pages/admin/add-service/add-service.component';
import { UpdateServiceComponent } from './pages/admin/update-service/update-service.component';
import { AddDiaFestivoComponent } from './pages/admin/add-dia-festivo/add-dia-festivo.component';
import { AddHorarioLaboralComponent } from './pages/admin/add-horario-laboral/add-horario-laboral.component';
import { UpdateNegocioComponent } from './pages/admin/update-negocio/update-negocio.component';
import { ViewCitasComponent } from './pages/admin/view-citas/view-citas.component';
import { ViewCitasDetailComponent } from './pages/admin/view-citas-detail/view-citas-detail.component';
import { AddClienteCitaComponent } from './pages/cliente/add-cliente-cita/add-cliente-cita.component';
import { MyChartComponent } from './pages/admin/my-chart/my-chart.component';





@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SignupComponent,
    LoginComponent,
    HomeComponent,
    DashboardComponent,
    UserDashboardComponent,
    TwoFaComponent,
    ProfileComponent,
    SidebarComponent,
    WelcomeComponent,
    ViewRecursosComponent,
    AddRecursosComponent,
    ViewRecursoDetailComponent,
    UpdateRecursoComponent,
    CalendarComponent,
    ViewUserDetailComponent,
    AddUsersComponent,
    ViewUsersComponent,
    AddEmpleadoComponent,
    ClienteDashboardComponent,
    SidebarClienteComponent,
    UpdateAdminUserComponent,
    UpdateClienteUserComponent,
    ViewServicesComponent,
    ViewServicesDetailComponent,
    AddServiceComponent,
    UpdateServiceComponent,
    AddDiaFestivoComponent,
    AddHorarioLaboralComponent,
    UpdateNegocioComponent,
    ViewCitasComponent,
    ViewCitasDetailComponent,
    AddClienteCitaComponent,
    MyChartComponent,



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
