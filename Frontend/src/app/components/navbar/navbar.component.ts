import { Component } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';
import { NegocioService } from 'src/app/services/negocio.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})


export class NavbarComponent {

  isLoggedIn = false;
  user:any = null;
  negocio:any;

  
  constructor (
    public login: LoginService,
    public negocioService: NegocioService
  ){}

  ngOnInit(): void {
    //this.cargarNegocio();
    this.isLoggedIn = this.login.isLoggedIn();
    this.user = this.login.getUser();
    this.login.loginStatusSubjec.asObservable().subscribe(
      data => {
        this.isLoggedIn = this.login.isLoggedIn();
        this.user = this.login.getUser();
      }
    )
  }

  public logout(){
    this.login.logout();
    window.location.reload();
  }

  /*private cargarNegocio() {
    this.negocioService.obtenerNegocioPorId(1).subscribe(
      (data: any) => {
        this.negocio = data;
      },
      error => {
        console.error('Error al obtener el negocio', error);
      }
    );
  }*/
}
