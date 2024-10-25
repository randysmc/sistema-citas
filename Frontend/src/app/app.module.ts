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


import { AddUsersComponent } from './pages/super-user/add-users/add-users.component';
import { ViewUsersComponent } from './pages/super-user/view-users/view-users.component';
import { AddNegociosComponent } from './pages/super-user/add-negocios/add-negocios.component';
import { ViewNegociosComponent } from './pages/super-user/view-negocios/view-negocios.component';
import { SuperuDashboardComponent } from './pages/super-user/superu-dashboard/superu-dashboard.component';
import { SuperuSidebarComponent } from './pages/super-user/superu-sidebar/superu-sidebar.component';
import { SuperuWelcomeComponent } from './pages/super-user/superu-welcome/superu-welcome.component';
import { DetalleNegocioComponent } from './pages/super-user/detalle-negocio/detalle-negocio.component';




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
    AddUsersComponent,
    ViewUsersComponent,
    AddNegociosComponent,
    ViewNegociosComponent,
    SuperuDashboardComponent,
    SuperuSidebarComponent,
    SuperuWelcomeComponent,
    DetalleNegocioComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule, 
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatSnackBarModule,
    MatCardModule,
    MatToolbarModule,
    MatIconModule,
    MatListModule,
    ReactiveFormsModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }