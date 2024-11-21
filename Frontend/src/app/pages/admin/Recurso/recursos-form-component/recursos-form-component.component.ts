import { Component, Input, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';
import { RecursoService } from 'src/app/services/recurso.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-recursos-form-component',
  templateUrl: './recursos-form-component.component.html',
  styleUrls: ['./recursos-form-component.component.css']
})

export class RecursosFormComponent implements OnInit {

  recursoId: number | null = null; // ID del recurso a editar
  recurso = {
    nombre: '',
    descripcion: '',
    disponible: true,
    tipo: 'PERSONAL'
  };

  tipos = ['PERSONAL', 'INSTALACION'];

  constructor(
    private recursoService: RecursoService,
    private snack: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // Verificar si hay un ID en la ruta (indica que estamos editando)
    this.recursoId = this.route.snapshot.params['id'] || null;

    if (this.recursoId) {
      this.cargarRecurso();
    }
  }

  cargarRecurso() {
    this.recursoService.obtenerRecursoPorId(this.recursoId!).subscribe(
      (data: any) => {
        this.recurso = data;
      },
      (error) => {
        Swal.fire('Error!', 'Error al cargar el recurso para editar', 'error');
      }
    );
  }

  formSubmit() {
    if (this.recurso.nombre.trim() === '' || this.recurso.nombre == null) {
      this.snack.open('El nombre es requerido !!', '', { duration: 3000 });
      return;
    }

    if (this.recursoId) {
      // Actualizar recurso existente
      this.recursoService.actualizarRecurso(this.recursoId, this.recurso).subscribe(
        () => {
          Swal.fire('Recurso actualizado', 'El recurso ha sido actualizado con éxito', 'success');
          this.router.navigate(['/admin/recursos']);
        },
        (error) => {
          Swal.fire('Error!', 'Error al actualizar el recurso', 'error');
        }
      );
    } else {
      // Crear nuevo recurso
      this.recursoService.agregarRecurso(this.recurso).subscribe(
        () => {
          Swal.fire('Recurso agregado', 'El recurso ha sido agregado con éxito', 'success');
          this.router.navigate(['/admin/recursos']);
        },
        (error) => {
          Swal.fire('Error!', 'Error al guardar el recurso', 'error');
        }
      );
    }
  }
}