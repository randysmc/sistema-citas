import { Routes } from '@angular/router';
import { EmpleadoDashboardComponent } from './pages/empleado/empleado-dashboard/empleado-dashboard.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { ViewCitasDetailEmpleadoComponent } from './pages/empleado/citas/view-citas-detail-empleado/view-citas-detail-empleado.component';
import { ViewCitasEmpleadoComponent } from './pages/empleado/citas/view-citas-empleado/view-citas-empleado.component';
import { EmpleadoUpdateProfileComponent } from './pages/empleado/empleado-update-profile/empleado-update-profile.component';


export const EmpleadoRoutes: Routes = [
    {
        path: '',
        component: EmpleadoDashboardComponent,
        children:[
            {
                path: 'profile',
                component: ProfileComponent
            },
            {
                path: 'update-profile/:id',
                component: EmpleadoUpdateProfileComponent
            },
            {
                path: '',
                component: ViewCitasEmpleadoComponent,
            },
            {
                path: 'citas/:id',
                component: ViewCitasDetailEmpleadoComponent
            }
            
        ]
        
        
    }
]