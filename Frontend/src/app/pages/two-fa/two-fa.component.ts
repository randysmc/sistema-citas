import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-two-fa',
  templateUrl: './two-fa.component.html',
  styleUrls: ['./two-fa.component.css']
})

export class TwoFaComponent {
  twoFactorCode: string = '';
  username: string = '';

  constructor(private route: ActivatedRoute, private loginService: LoginService, private router: Router, private snack: MatSnackBar){
    //Recuperamos el usario
    this.username = localStorage.getItem('username') || '';

  }

  ngOnInit():void{
    this.route.paramMap.subscribe(params => {
      this.username = params.get('username') || '';
    });
  }


  validateTwoFactorCode() {
    if (this.twoFactorCode.trim() === '' || this.twoFactorCode == null) {
      this.snack.open('El código 2FA es requerido', 'Aceptar', {
        duration: 3000
      });
      return;
    }
  
    const twoFactorData = {
      username: this.username,
      twoFactorCode: this.twoFactorCode
    };
  
    this.loginService.validateTwoFactorCode(twoFactorData).subscribe(
      (response: any) => {
        this.loginService.loginUser(response.token);
        
        this.loginService.getCurrentUser().subscribe((user: any) => {
          this.loginService.setUser(user);
          if (this.loginService.getUserRole() === "ADMINISTRADOR") {
            this.router.navigate(['admin']);
          } else if (this.loginService.getUserRole() === "CLIENTE") {
            this.router.navigate(['cliente']);
          } else if(this.loginService.getUserRole() == "EMPLEADO"){
            this.router.navigate(['empleado'])
          }
        });
      },
      (error) => {
        console.log(error);
        this.snack.open('Código de verificación inválido, vuelva a intentar!', 'Aceptar', {
          duration: 3000
        });
      }
    );
  }
  

}
