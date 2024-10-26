import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-users',
  templateUrl: './view-users.component.html',
  styleUrls: ['./view-users.component.css']
})
export class ViewUsersComponent implements OnInit {
  usuarios: any = [];
  usuariosActivados: any = [];
  usuariosDesactivados: any = [];
  filtroSeleccionado: string = 'todos'; // valor inicial del filtro

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.listarUsuarios();
    this.listarUsuariosActivados();
    this.listarUsuariosDesactivados();
  }

  listarUsuarios() {
    this.userService.obtenerUsuarios().subscribe(
      (dato: any) => {
        this.usuarios = dato;
      },
      (error) => {
        Swal.fire('Error!', 'Error al cargar los usuarios disponibles', 'error');
      }
    );
  }

  listarUsuariosActivados() {
    this.userService.obtenerUsuariosActivados().subscribe(
      (dato: any) => {
        this.usuariosActivados = dato; // Cambiado a usuariosActivados
      },
      (error) => {
        Swal.fire('Error!', 'Error al cargar los usuarios activados', 'error');
      }
    );
  }

  listarUsuariosDesactivados() {
    this.userService.obtenerUsuariosDesactivados().subscribe(
      (dato: any) => {
        this.usuariosDesactivados = dato; // Cambiado a usuariosDesactivados
      },
      (error) => {
        Swal.fire('Error!', 'Error al cargar los usuarios no activados', 'error');
      }
    );
  }

  verDetalle(id:number) {
    this.router.navigate(['/admin/users', id]);
  }
}
