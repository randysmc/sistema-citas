import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RecursoService } from 'src/app/services/recurso.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-recurso-detail',
  templateUrl: './view-recurso-detail.component.html',
  styleUrls: ['./view-recurso-detail.component.css']
})
export class ViewRecursoDetailComponent {

  recurso: any = null;
  id: number = 0;

  constructor(
    private route: ActivatedRoute,
    private recursoService: RecursoService,
    private router: Router
  ) {}

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.cargarRecurso(this.id);
  }

  cargarRecurso(id: number) {
    this.recursoService.obtenerRecursoPorId(id).subscribe(
      (dato: any) => {
        this.recurso = dato;
      },
      (error) => {
        console.log(error);
        Swal.fire('Error', 'Error al cargar el recurso', 'error');
      }
    );
  }

  // Método para actualizar recurso (puedes agregar la lógica que necesites)
  actualizarRecurso() {
    this.router.navigate([`/admin/update-recurso/${this.recurso.recursoId}`]);
  }
  
  

  // Activar recurso
  activarRecurso() {
    this.recursoService.habilitarRecurso(this.id).subscribe({
      next: (resp) => {
        console.log('Recurso activado:', resp);
        // Actualiza el estado del recurso localmente
        this.recurso.disponible = true; 
        Swal.fire('Éxito', 'Recurso activado correctamente', 'success').then(() => {
          this.cargarRecurso(this.id); // Recargar el recurso
        });
      },
      error: (err) => {
        console.error('Error al activar recurso', err);
        Swal.fire('Error', 'Error al activar el recurso', 'error');
      }
    });
  }

  // Inactivar recurso
  inactivarRecurso() {
    this.recursoService.deshabilitarRecurso(this.id).subscribe({
      next: (resp) => {
        console.log('Recurso inactivado:', resp);
        // Actualiza el estado del recurso localmente
        this.recurso.disponible = false; 
        Swal.fire('Éxito', 'Recurso inactivado correctamente', 'success').then(() => {
          this.cargarRecurso(this.id); // Recargar el recurso
        });
      },
      error: (err) => {
        console.error('Error al inactivar recurso', err);
        Swal.fire('Error', 'Error al inactivar el recurso', 'error');
      }
    });
  }
}
