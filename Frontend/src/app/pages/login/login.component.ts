import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginData = {
    "username" : '',
    "password" : ''
  }

  constructor(
    private snack:MatSnackBar,
    private loginService:LoginService,
    private router:Router
  ) { }

  ngOnInit():void {
    
  }

  formSubmit(){
    if(this.loginData.username.trim() == '' || this.loginData.username.trim == null){
      this.snack.open('El nombre de usuario es requerido!', 'Aceptar', {
        duration:4000
      })
      return
    }

    if(this.loginData.password.trim() == '' || this.loginData.password.trim == null){
      this.snack.open('password requerida!', 'Aceptar', {
        duration:4000
      })
      return
    }

    this.loginService.generateToken(this.loginData).subscribe(
      (data:any) => {
        console.log(data);
        this.loginService.loginUser(data.token);
        this.loginService.getCurrentUser().subscribe((user:any) => {
          this.loginService.setUser(user)
          console.log(user);

          if(this.loginService.getUserRole() == "ADMIN"){
            //window.location.href = "/admin"
            this.router.navigate(['admin'])
            this.loginService.loginStatusSubjec.next(true);
          }
          else if(this.loginService.getUserRole() == "NORMAL"){
            //window.location.href = "/user-dashboard"
            this.router.navigate(['user-dashboard'])
            this.loginService.loginStatusSubjec.next(true);
          }
          else{
            this.loginService.logout();
          }
        })
      }, (error) => {
        console.log(error);
        this.snack.open('Credenciales invalidas, vuelva a intentar! ', 'Aceptar ', {
          duration:3000
        })
      }
    )
  }

}
