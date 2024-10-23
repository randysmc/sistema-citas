import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { SignupComponent } from './pages/signup/signup.component';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/admin/dashboard/dashboard.component';
import { UserDashboardComponent } from './pages/user/user-dashboard/user-dashboard.component';
import { AdminGuard } from './services/admin.guard';
import { normalGuard } from './services/normal.guard';
import { superusuarioGuard } from './services/superusuario.guard'
import { TwoFaComponent } from './pages/two-fa/two-fa.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { WelcomeComponent } from './pages/admin/welcome/welcome.component';
import { ViewRecursosComponent } from './pages/admin/view-recursos/view-recursos.component';
import { AddRecursosComponent } from './pages/admin/add-recursos/add-recursos.component';
import { SuperuDashboardComponent } from './pages/super-user/superu-dashboard/superu-dashboard.component';
import { ViewNegociosComponent } from './pages/super-user/view-negocios/view-negocios.component';
import { AddNegociosComponent } from './pages/super-user/add-negocios/add-negocios.component';
import { AddUsersComponent } from './pages/super-user/add-users/add-users.component';
import { ViewUsersComponent } from './pages/super-user/view-users/view-users.component';
import { DetalleNegocioComponent } from './pages/super-user/detalle-negocio/detalle-negocio.component';

const routes: Routes = [
  {
    path:'',
    component:HomeComponent,
    pathMatch: 'full'
  },
  {
    path:'signup',
    component:SignupComponent,
    pathMatch: 'full'
  },
  {
    path:'login',
    component:LoginComponent,
    pathMatch: 'full'
  },

  {
    path: 'superusuario',
    component:SuperuDashboardComponent,
    //canActivate:[superusuarioGuard],
    children:[
      {
        path:'profile',
        component:ProfileComponent
      },
      {
        path:"negocios",
        component:ViewNegociosComponent
      },
      {
        path:'add-negocio',
        component:AddNegociosComponent
      },
      {
        path:'negocios/:id',
        component:DetalleNegocioComponent
      },
      {
        path:'users',
        component:ViewUsersComponent
      },
      {
        path:'add-user',
        component:AddUsersComponent
      }

    ]
  },
  {
    path: 'admin',
    component:DashboardComponent,
    //canActivate:[AdminGuard],
    children:[
      {
        path:'profile',
        component:ProfileComponent
      },
      {
        path: '',
        component:WelcomeComponent
      },
      {
        path: 'recursos',
        component:ViewRecursosComponent
      },
      {
        path: 'add-recurso',
        component:AddRecursosComponent
      }
    ]
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
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
