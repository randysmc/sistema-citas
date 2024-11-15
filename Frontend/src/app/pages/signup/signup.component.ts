import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit{

  public user = {
    username : '',
    password : '',
    nombre: '',
    apellido: '',
    email: '',
    perfil: 'default.jpg',
    telefono: '',
    cui: '',
    nit: '',
    roles: [3]

  }

  constructor(
    private userService:UserService,
    private snack:MatSnackBar,
    private router: Router  
  ){ }

  ngOnInit(): void {
    
  }

  formSubmit(){
    console.log(this.user);
    if(this.user.username == '' || this.user.username == null){
      this.snack.open("El nombre del usuario es requerido!!", "Aceptar", {
        duration:5000,
        verticalPosition: 'top',
        horizontalPosition: 'right'
      });
      return; 
    }

    this.userService.registrarUsuario(this.user).subscribe(
      (data) =>  {
        console.log(data);
        Swal .fire('Usuario registrado', 'usuario registrado con exito en el sistema', 'success')
        this.router.navigate(['login']);
      },(error) => {
        console.log(error);
        this.snack.open("Ha ocurrido un error!", "Aceptar", {
          duration:5000,
          verticalPosition: 'top',
          horizontalPosition: 'right'
        });
      }
    )
  }

}
