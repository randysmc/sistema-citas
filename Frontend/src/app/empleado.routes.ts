import { Routes } from '@angular/router';
import { EmpleadoDashboardComponent } from './pages/empleado/empleado-dashboard/empleado-dashboard.component';
import { ProfileComponent } from './pages/profile/profile.component';


export const EmpleadoRoutes: Routes = [
    {
        path: '',
        component: EmpleadoDashboardComponent,
        children:[
            {
                path: 'profile',
                component: ProfileComponent
            }
            
        ]
        
        
    }
]