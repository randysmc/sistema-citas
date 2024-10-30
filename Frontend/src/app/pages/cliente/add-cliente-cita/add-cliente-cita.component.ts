import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from 'src/app/services/login.service';
import { CitasService } from 'src/app/services/citas.service';
import { RecursoService } from 'src/app/services/recurso.service';
import { EmpleadoService } from 'src/app/services/empleado.service';
import { ServiceService } from 'src/app/services/service.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-cliente-cita',
  templateUrl: './add-cliente-cita.component.html',
  styleUrls: ['./add-cliente-cita.component.css']
})
export class AddClienteCitaComponent implements OnInit {

  citaForm: FormGroup;
  usuarioAutenticado: any;
  recursosDisponibles: any[] = [];
  empleadosDisponibles: any[] = [];
  serviciosDisponibles: any[] = [];
  esTipoPersonal: boolean = false;

  constructor(
    private loginService: LoginService,
    private citasService: CitasService,
    private recursosService: RecursoService,
    private empleadosService: EmpleadoService,
    private serviceService: ServiceService,
    private fb: FormBuilder,
    private snack: MatSnackBar,
    private router: Router
  ) {
    this.citaForm = this.fb.group({
      fecha: ['', Validators.required],
      horaInicio: ['', Validators.required],
      recurso: ['', Validators.required],
      empleado: [''],
      servicio: ['', Validators.required] // Asegúrate de incluir el servicio aquí
    });
  }

  ngOnInit(): void {
    this.usuarioAutenticado = this.loginService.getUser();
    this.obtenerRecursos();
    this.obtenerServiciosDisponibles();
  }

  obtenerRecursos() {
    this.recursosService.obtenerRecursosDisponibles().subscribe(
      (recursos: any) => {
        this.recursosDisponibles = recursos as any[];
      },
      error => {
        console.error('Error al obtener recursos:', error);
        this.snack.open('Error al cargar recursos', '', { duration: 3000 });
      }
    );
  }

  onRecursoChange(event: any) {
    const recursoId = event.target.value;
    const recursoSeleccionado = this.recursosDisponibles.find(recurso => recurso.recursoId == recursoId);

    if (recursoSeleccionado) {
      this.esTipoPersonal = recursoSeleccionado.tipo === 'PERSONAL';
      if (this.esTipoPersonal) {
        this.obtenerEmpleadosDisponibles();
      } else {
        this.citaForm.patchValue({ empleado: null });
      }
    }
  }

  obtenerEmpleadosDisponibles() {
    this.empleadosService.obtenerEmpleadosActivados().subscribe(
      (empleados: any) => {
        this.empleadosDisponibles = empleados as any[];
      },
      error => {
        console.error('Error al obtener empleados:', error);
        this.snack.open('Error al cargar empleados', '', { duration: 3000 });
      }
    );
  }

  obtenerServiciosDisponibles() {
    this.serviceService.obtenerServiciosDisponible().subscribe(
      (servicios: any) => {
        this.serviciosDisponibles = servicios as any[];
      },
      error => {
        console.error('Error al obtener los servicios:', error);
        this.snack.open('Error al cargar servicios', '', { duration: 3000 });
      }
    );
  }

  onSubmit() {
    if (this.citaForm.invalid) {
      this.snack.open('Por favor completa todos los campos requeridos', '', { duration: 3000 });
      return;
    }

    const citaData = {
      fecha: this.citaForm.value.fecha,
      horaInicio: this.citaForm.value.horaInicio,
      cliente: { id: this.usuarioAutenticado.id },
      empleado: this.esTipoPersonal ? { id: this.citaForm.value.empleado } : null,
      recurso: { recursoId: this.citaForm.value.recurso },
      servicio: { servicioId: this.citaForm.value.servicio }
    };

    this.citasService.crearCita(citaData).subscribe(
      response => {
        Swal.fire('Cita creada', 'La cita se ha creado con éxito', 'success');
        this.citaForm.reset();
        this.router.navigate(['/cliente/calendario']); // Cambia esta ruta a donde desees redirigir
      },
      error => {
        console.error('Error al crear la cita:', error);
        let errorMessage = 'Error al crear la cita'; // Mensaje por defecto

        // Comprobar si el error tiene un mensaje específico
        if (error.error && error.error.details) {
          errorMessage = error.error.details; // Mostrar el mensaje de detalle del error
        }

        Swal.fire('Error !!', errorMessage, 'error');
      }
    );
  }
}
