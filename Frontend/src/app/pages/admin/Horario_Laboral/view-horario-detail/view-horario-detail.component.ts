import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HorariosLaboralesService } from 'src/app/services/horarios-laborales.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-view-horario-detail',
  templateUrl: './view-horario-detail.component.html',
  styleUrls: ['./view-horario-detail.component.css']
})
export class ViewHorarioDetailComponent {
  horario: any = null;
  id: number=0;

  constructor(
    private route: ActivatedRoute,
    private horarioLaboralService: HorariosLaboralesService,
    private router: Router
  ){}

  ngOnInit(){
    this.id = this.route.snapshot.params['id'];
    this.cargarHorario(this.id);
  }


  cargarHorario(id: number){
    this.horarioLaboralService.obtenerHorarioPorId(id).subscribe(
      (dato:any) => {
        console.log(dato);
        this.horario = dato;
      },
      (error)=>{
        Swal.fire('Error', 'Error al cargar el horario', 'error')
      }
    )
  }

  actualizarHorario(){
    this.router.navigate([`/admin/update-horario-laboral/${this.horario.horarioLaboralId}`])
  }

  regresar(){
    this.router.navigate([`/admin/horarios-laborales`])
  }


  eliminarHorario() {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'No podrás deshacer esta acción',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.horarioLaboralService.eliminarHorario(this.horario.horarioLaboralId).subscribe(
          () => {
            Swal.fire('Eliminado', 'El horario ha sido eliminado', 'success');
            this.router.navigate(['/admin/horarios-laborales']); // Redirige al listado
          },
          (error) => {
            Swal.fire('Error', 'Error al eliminar el horario', 'error');
          }
        );
      }
    });
  }
  


}
