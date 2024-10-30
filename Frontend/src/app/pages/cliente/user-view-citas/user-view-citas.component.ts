import { Component, OnInit } from '@angular/core';
import { CitasService } from 'src/app/services/citas.service';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service'; // Asegúrate de importar el servicio de login
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-view-citas',
  templateUrl: './user-view-citas.component.html',
  styleUrls: ['./user-view-citas.component.css']
})
export class UserViewCitasComponent implements OnInit {
  citas: any[] = [];
  filtroSeleccionado: string = 'todos'; // valor inicial del filtro
  selectedUsuario: number | null = null; // Usuario seleccionado
  userId!: number; // Almacena el ID del usuario

  constructor(
    private citasService: CitasService, 
    private router: Router,
    private loginService: LoginService // Inyecta el servicio de login
  ) {}

  ngOnInit(): void {
    this.loginService.getCurrentUser().subscribe(
      (user: any) => {
        this.userId = user.id; // Almacena el ID del usuario
        this.listarCitas(); // Llama al método aquí después de obtener el usuario
      },
      (error) => {
        console.error('Error al obtener el usuario:', error);
      }
    );
  }

  listarCitas() {
    if (this.userId) {
      this.citasService.obtenerCitasPorUsuario(this.userId).subscribe(
        (dato: any) => {
          this.citas = dato;
          console.log(this.citas); // Verifica qué datos se están recibiendo
        },
        (error) => {
          Swal.fire('Error!', 'Error al cargar las citas', 'error');
        }
      );
    }
  }

  verDetalle(id: number) {
    this.router.navigate(['/cliente/citas', id]); // Navegar a la vista de detalle de la cita
  }
}
