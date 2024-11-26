import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EmpleadoService } from 'src/app/services/empleado.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-empleados',
  templateUrl: './view-empleados.component.html',
  styleUrls: ['./view-empleados.component.css']
})
export class ViewEmpleadosComponent implements OnInit {
  empleados: any = [];
  empleadosActivados: any = [];
  empleadosDesactivados: any = [];
  filtroSeleccionado: string = 'todos';

  constructor(
    private empleadoService: EmpleadoService,
    private router: Router,
  ){}

  ngOnInit(): void {
    this.listarEmpleados();
    this.listarEmpleadosActivados();
    this.listarEmpleadosDesactivados();
  }


  listarEmpleados() {
    this.empleadoService.obtenerEmpleados().subscribe(
      (dato: any) => {
        this.empleados = dato;
      },
      (error) => {
        Swal.fire('Error!', 'Error al cargar los empleados disponibles', 'error');
      }
    );
  }

  listarEmpleadosActivados(){
    this.empleadoService.obtenerEmpleadosActivados().subscribe(
      (dato: any) => {
        this.empleadosActivados = dato; // Cambiado a usuariosActivados
      },
      (error) => {
        Swal.fire('Error!', 'Error al cargar los usuarios activados', 'error');
      }
    );
  }

  listarEmpleadosDesactivados(){
    this.empleadoService.obtenerEmpleadosDesactivados().subscribe(
      (dato: any) => {
        this.empleadosDesactivados = dato; // Cambiado a usuariosDesactivados
      },
      (error) => {
        Swal.fire('Error!', 'Error al cargar los usuarios no activados', 'error');
      }
    );
  }

  verDetalle(id:number) {
    this.router.navigate(['/admin/empleado', id]);
  }

  agregarEmpleado(){
    this.router.navigate([`/admin/add-empleado`])
  }

}
