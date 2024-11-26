import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CitasService } from 'src/app/services/citas.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-view-citas-detail',
  templateUrl: './view-citas-detail.component.html',
  styleUrls: ['./view-citas-detail.component.css']
})
export class ViewCitasDetailComponent {

  cita: any=null;
  id: number =0

  constructor(
    private route: ActivatedRoute,
    private citasService: CitasService,
    private router: Router
  ){

  }


  ngOnInit(){
    this.id = this.route.snapshot.params['id'];
    this.cargarCita(this.id)
  }


  cargarCita(id: number){
    this.citasService.obtenerCitaPorId(id).subscribe(
      (dato:any)=>{
        this.cita = dato;
      },
      (error) => {
        console.log(error);
        Swal.fire('Error')
      }
    )
  }

  cancelarCita(): void {
    if (this.cita.estado === 'AGENDADA') {
      this.citasService.cancelarCita(this.cita.idCita).subscribe(
        () => {
          Swal.fire('Cita cancelada', '', 'success');
          this.cita.estado = 'CANCELADA'; // Actualiza el estado de la cita localmente
        },
        (error) => {
          console.log(error);
          Swal.fire('Error al cancelar la cita');
        }
      );
    }
  }

  realizarCita(): void {
    if (this.cita.estado !== 'REALIZADA' && this.cita.estado !== 'CANCELADA') {
      this.citasService.completarCita(this.cita.idCita).subscribe(
        () => {
          Swal.fire('Cita realizada', '', 'success');
          this.cita.estado = 'REALIZADA'; // Actualiza el estado de la cita localmente
        },
        (error) => {
          console.log(error);
          Swal.fire('Error al realizar la cita');
        }
      );
    }
  }
}
