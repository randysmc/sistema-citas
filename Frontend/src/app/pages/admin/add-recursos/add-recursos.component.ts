import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { RecursoService } from 'src/app/services/recurso.service';
import { LoginService } from 'src/app/services/login.service';  // Importamos el LoginService
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-recursos',
  templateUrl: './add-recursos.component.html',
  styleUrls: ['./add-recursos.component.css']
})
export class AddRecursosComponent {

  recurso = {
    nombre: '',
    descripcion: '',
    negocio: {
      negocioId: 1 // Lo inicializamos temporalmente
    }
  }

  constructor(
    private recursoService: RecursoService,
    private snack: MatSnackBar,
    private router: Router,
    private loginService: LoginService  // Inyectamos el LoginService
  ) {}

  ngOnInit(): void {
    // Obtenemos el usuario actual y asignamos el negocioId
    this.loginService.getCurrentUser().subscribe(
      (user: any) => {
        if (user && user.negocios && user.negocios.length > 0) {
          this.recurso.negocio.negocioId = user.negocios[0].id; // Asignamos el negocioId del usuario logueado
        }
      },
      (error) => {
        console.log('Error al obtener el usuario', error);
      }
    );
  }

  formSubmit() {
    if (this.recurso.nombre.trim() == '' || this.recurso.nombre == null) {
      this.snack.open("El título es requerido !!", '', {
        duration: 3000
      });
      return;
    }

    // Llamamos al servicio para agregar el recurso
    this.recursoService.agregarRecurso(this.recurso).subscribe(
      (dato: any) => {
        this.recurso.nombre = '';
        this.recurso.descripcion = '';
        Swal.fire('Recurso agregado', 'El recurso ha sido agregado con éxito', 'success');
        this.router.navigate(['/admin/recursos']);
      },
      (error) => {
        console.log(error);
        Swal.fire('Error !!', 'Error al guardar la categoría', 'error');
      }
    );
  }
}
