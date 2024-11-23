import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CitasService } from 'src/app/services/citas.service';
import { LoginService } from 'src/app/services/login.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-citas-empleado',
  templateUrl: './view-citas-empleado.component.html',
  styleUrls: ['./view-citas-empleado.component.css']
})
export class ViewCitasEmpleadoComponent implements OnInit {
  citas: any[] = [];
  userId!: number

  constructor(
    private citasService : CitasService,
    private loginService : LoginService,
    private router: Router,
  ){}


  ngOnInit(): void {
    this.loginService.getCurrentUser().subscribe(
      (user:any)=>{
        this.userId = user.id
        this.listarCitas();
      },
      (error) => {
        
      }
    )
  }

  listarCitas(){
    if(this.userId){
      this.citasService.obtenerCitasPorEmpleado(this.userId).subscribe(
        (dato:any)=>{
          this.citas = dato;
          console.log(dato);
        },
        (error) =>{
          Swal.fire('Error', 'Error nal cargar las citas', 'error')
        }
      )
    }
  }

  verDetalle(id:number){
    this.router.navigate(['/empleado/citas', id]) //navegamos al detalle de cita
  }


}
