import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ServiceService } from 'src/app/services/service.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-user-view-services-detail',
  templateUrl: './user-view-services-detail.component.html',
  styleUrls: ['./user-view-services-detail.component.css']
})
export class UserViewServicesDetailComponent {

  servicio: any = null;
  id: number = 0;

  constructor(
    private route: ActivatedRoute,
    private servicioService: ServiceService,
    private router: Router
  ) {}

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.cargarServicio(this.id);
  }


  cargarServicio(id: number) {
    this.servicioService.obtenerServicioPorId(id).subscribe(
      (dato: any) => {
        this.servicio = dato;
      },
      (error) => {
        console.log(error);
        Swal.fire('Error', 'Error al cargar el servicio', 'error');
      }
    );
  }

  


}
