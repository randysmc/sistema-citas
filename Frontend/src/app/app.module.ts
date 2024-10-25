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
import { UpdateUserComponent } from './pages/admin/update-user/update-user.component';
import { ViewUsersComponent } from './pages/admin/view-users/view-users.component';





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
    UpdateUserComponent,
    AddUsersComponent,
    ViewUsersComponent

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
    FullCalendarModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
