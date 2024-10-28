import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RecursoService } from 'src/app/services/recurso.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-update-recurso',
  templateUrl: './update-recurso.component.html',
  styleUrls: ['./update-recurso.component.css']
})
export class UpdateRecursoComponent implements OnInit {

  recursoId!: number;
  recurso = {
    nombre: '',
    descripcion: '',
    disponible: true,
    tipo: 'PERSONAL'
  };

  tipos = ['PERSONAL', 'INSTALACION'];

  constructor(
    private route: ActivatedRoute,
    private recursoService: RecursoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Obtener el ID del recurso desde la ruta
    this.recursoId = this.route.snapshot.params['id'];

    // Cargar el recurso existente para actualizarlo
    this.recursoService.obtenerRecursoPorId(this.recursoId).subscribe(
      (data: any) => {
        this.recurso = data;  // Cargar los detalles del recurso
      },
      (error) => {
        console.log('Error al cargar el recurso', error);
      }
    );
  }

  formSubmit() {
    if (this.recurso.nombre.trim() === '' || this.recurso.nombre == null) {
      Swal.fire('El título es requerido', '', 'warning');
      return;
    }

    // Llamada al servicio para actualizar el recurso
    this.recursoService.actualizarRecurso(this.recursoId, this.recurso).subscribe(
      (data) => {
        Swal.fire('Recurso actualizado', 'El recurso ha sido actualizado con éxito', 'success');
        this.router.navigate(['/admin/recursos']);
      },
      (error) => {
        console.log('Error al actualizar el recurso', error);
        Swal.fire('Error', 'Hubo un problema al actualizar el recurso', 'error');
      }
    );
  }
}
