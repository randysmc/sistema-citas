import { Routes } from "@angular/router";
import { ClienteDashboardComponent } from "./pages/cliente/cliente-dashboard/cliente-dashboard.component";
import { ProfileComponent } from "./pages/profile/profile.component";
import { UpdateClienteUserComponent } from "./pages/cliente/update-cliente-user/update-cliente-user.component";
import { AddClienteCitaComponent } from "./pages/cliente/add-cliente-cita/add-cliente-cita.component";
import { UserViewServicesComponent } from "./pages/cliente/user-view-services/user-view-services.component";
import { UserViewServicesDetailComponent } from "./pages/cliente/user-view-services-detail/user-view-services-detail.component";
import { ViewServicesComponent } from "./pages/admin/Servicio/view-services/view-services.component";
import { UserViewFacturasComponent } from "./pages/cliente/user-view-facturas/user-view-facturas.component";
import { UserViewFacturasDetailComponent } from "./pages/cliente/user-view-facturas-detail/user-view-facturas-detail.component";
import { UserViewCitasComponent } from "./pages/cliente/user-view-citas/user-view-citas.component";
import { UserViewCitasDetailComponent } from "./pages/cliente/user-view-citas-detail/user-view-citas-detail.component";
import { WelcomeComponent } from "./pages/admin/welcome/welcome.component";

export const ClienteRoutes: Routes = [
    {
        path: '',
        component: ClienteDashboardComponent,
        children:[
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
        
        ]
    }
]