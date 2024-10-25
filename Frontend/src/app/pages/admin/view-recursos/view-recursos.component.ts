import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RecursoService } from 'src/app/services/recurso.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-recursos',
  templateUrl: './view-recursos.component.html',
  styleUrls: ['./view-recursos.component.css']
})
export class ViewRecursosComponent implements OnInit {

  recursos: any = [];
  recursosDisponibles: any = [];
  recursosNoDisponibles: any = [];
  filtroSeleccionado: string = 'todos'; // valor inicial del filtro

  constructor(private recursoService: RecursoService, private router: Router) {}

  ngOnInit(): void {
    this.listarTodosRecursos();
    this.listarRecursosDisponibles();
    this.listarRecursosNoDisponibles();
  }

  listarTodosRecursos() {
    this.recursoService.listarRecursos().subscribe(
      (dato: any) => {
        this.recursos = dato;
      },
      (error) => {
        Swal.fire('Error!', 'Error al cargar los recursos', 'error');
      }
    );
  }

  listarRecursosDisponibles() {
    this.recursoService.obtenerRecursosDisponibles().subscribe(
      (dato: any) => {
        this.recursosDisponibles = dato;
      },
      (error) => {
        Swal.fire('Error!', 'Error al cargar los recursos disponibles', 'error');
      }
    );
  }

  listarRecursosNoDisponibles() {
    this.recursoService.obtenerRecursosNoDisponibles().subscribe(
      (dato: any) => {
        this.recursosNoDisponibles = dato;
      },
      (error) => {
        Swal.fire('Error!', 'Error al cargar los recursos no disponibles', 'error');
      }
    );
  }

  verDetalle(id: number) {
    this.router.navigate(['/admin/recursos', id]);
  }
}
