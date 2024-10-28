import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RolService } from 'src/app/services/rol.service';
import { UserService } from 'src/app/services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-users',
  templateUrl: './add-users.component.html',
  styleUrls: ['./add-users.component.css']
})
export class AddUsersComponent implements OnInit {
  user: any = {
    username: '',
    password: '',
    nombre: '',
    apellido: '',
    email: '',
    perfil:'default',
    telefono: '',
    cui: '',
    nit: '',
    roles: null // Campo para el rol
  };

  roles: any[] = []; // Para almacenar los roles

  constructor(private userService: UserService, private rolService: RolService, private router: Router) { }

  ngOnInit(): void {
    this.cargarRoles(); // Cargar los roles al inicializar el componente
  }


  cargarRoles() {
    this.rolService.listarRoles().subscribe((data: any) => {
        // Filtrar los roles para excluir 'CLIENTE' y 'ADMINISTRADOR'
        this.roles = data.filter((rol: any) => rol.rolNombre !== 'CLIENTE' && rol.rolNombre !== 'ADMINISTRADOR');
    }, (error) => {
        console.error('Error al cargar roles', error);
    });
}



  formSubmit() {
    // Asegúrate de que rolId se envíe como un arreglo
    const userToSend = {
        ...this.user,
        roles: [this.user.rolId] // Envía rolId como un arreglo
    };

    console.log('Usuario a registrar:', userToSend); // Verifica el objeto a enviar
    this.userService.registrarUsuario(userToSend).subscribe(
        (response) => {
            console.log('Usuario registrado', response);
            Swal.fire('Usuario registrado', 'usuario registrado con éxito en el sistema', 'success');
            this.router.navigate(['/users']); // Redirigir a la vista de usuarios
        },
        (error) => {
            console.error('Error al registrar usuario', error);
        }
    );
}

}
