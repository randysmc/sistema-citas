import { Component, OnInit } from '@angular/core';
import { CitasService } from 'src/app/services/citas.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-citas',
  templateUrl: './view-citas.component.html',
  styleUrls: ['./view-citas.component.css']
})
export class ViewCitasComponent {

  citas: any[] = [];
  filtroSeleccionado: string = 'todos'; // valor inicial del filtro
  selectedUsuario: number | null = null; // Usuario seleccionado

  constructor(
    private citasService:CitasService, 
    private router:Router){}

  
    ngOnInit(): void {
      this.listarCitas(); // Llama al método aquí
    }


    listarCitas() {
      this.citasService.obtenerCitas().subscribe(
        (dato: any) => {
          this.citas = dato;
          console.log(this.citas); // Verifica qué datos se están recibiendo
        },
        (error) => {
          Swal.fire('Error!', 'Error al cargar las citas', 'error');
        }
      );
    }
    

  verDetalle(id: number) {
    this.router.navigate(['/admin/citas', id]); // Navegar a la vista de detalle de la cita
  }

}
