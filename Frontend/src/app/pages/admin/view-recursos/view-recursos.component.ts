import { Component } from '@angular/core';
import { RecursoService } from 'src/app/services/recurso.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-recursos',
  templateUrl: './view-recursos.component.html',
  styleUrls: ['./view-recursos.component.css']
})
export class ViewRecursosComponent {

  recursos:any = [

  ]

  constructor(private recursoService:RecursoService){

  }

  ngOnInit(): void{
    this.recursoService.listarRecursos().subscribe(
      (dato:any) => {
        this.recursos = dato;
        console.log(this.recursos);
      },
      (error)=>{
        console.log(error);
        Swal.fire('Error!', 'Error al cargar los recursos', 'error')
      }
    )
  }
}
