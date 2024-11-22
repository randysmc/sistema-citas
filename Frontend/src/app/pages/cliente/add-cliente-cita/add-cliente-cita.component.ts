import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from 'src/app/services/login.service';
import { NegocioService } from 'src/app/services/negocio.service';
import { ServiceService } from 'src/app/services/service.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RecursoService } from 'src/app/services/recurso.service';
import { EmpleadoService } from 'src/app/services/empleado.service';
import { CitasService } from 'src/app/services/citas.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-cliente-cita',
  templateUrl: './add-cliente-cita.component.html',
  styleUrls: ['./add-cliente-cita.component.css']
})
export class AddClienteCitaComponent implements OnInit {
  citasAleatorias: boolean = false;
  citaForm: FormGroup;
  usuarioAutenticado: any;
  mensajeFormulario: string = '';
  serviciosDisponibles: any[] = [];
  recursosDisponibles: any[] = [];
  esTipoPersonal: boolean = false;
  empleadosDisponibles: any[] = [];
  mostrarEmpleados: boolean = false;


  constructor(
    private negocioService: NegocioService,
    private loginService: LoginService,
    private fb: FormBuilder,
    private serviceService: ServiceService,
    private snack: MatSnackBar,
    private recursosService: RecursoService,
    private empleadosService: EmpleadoService,
    private citaService: CitasService,
    private router : Router,
  ) {
    this.citaForm = this.fb.group({
      fecha: ['', Validators.required],
      horaInicio: ['', Validators.required],
      servicio: ['', Validators.required],  // Asegúrate de agregar el campo 'servicio'
      recurso: ['', Validators.required],
      empleado: [''],
    });
  }

  ngOnInit(): void {
    this.usuarioAutenticado = this.loginService.getUser();
    this.obtenerNegocio();
    this.obtenerServiciosDisponibles(); 
  }

  // Obtener configuración del negocio
  obtenerNegocio() {
    this.negocioService.obtenerNegocioPorId(1).subscribe(
      (negocio: any) => {
        this.citasAleatorias = negocio.citasAleatorias;
      },
      error => {
        console.error('Hubo un problema al obtener la configuración del negocio', error);
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

  obtenerRecursosDisponibles() {
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

  onServicioChange(event: any) {
    const servicioId = event.target.value;
    const servicioSeleccionado = this.serviciosDisponibles.find(servicio => servicio.servicioId == servicioId);
  
    if (servicioSeleccionado) {
      const tipo = servicioSeleccionado.tipo;
      if (tipo === 'PERSONAL') {
        this.obtenerRecursos(tipo);
        this.obtenerEmpleadosDisponibles(); // Obtener empleados solo para tipo PERSONAL
        this.mostrarEmpleados = true; // Controla si se muestran los empleados
      } else {
        this.obtenerRecursos(tipo);
        this.mostrarEmpleados = false; // No mostrar empleados para tipo INSTALACION
      }
    }
  }
  




obtenerRecursos(tipo: string) {
  this.recursosService.obtenerRecursos(tipo).subscribe(
      (recursos: any) => {
          this.recursosDisponibles = recursos as any[];
      },
      error => {
          this.snack.open('Error al cargar recursos', '', { duration: 3000 });
      }
  );
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


  // Método para guardar cita
  guardarCita() {
    if (this.citaForm.invalid) {
      this.snack.open('Por favor completa todos los campos requeridos', '', { duration: 3000 });
      return;
    }
  
    let citaData: any;
  
    if (this.citasAleatorias) {
      // Construcción de datos para cita aleatoria
      citaData = {
        fecha: this.citaForm.value.fecha,
        horaInicio: this.citaForm.value.horaInicio,
        servicio: { servicioId: this.citaForm.value.servicio },
        cliente: { id: this.usuarioAutenticado.id },
        recurso: { recursoId: this.citaForm.value.recurso },
      };
  
      this.citaService.crearCitaAleatoria(citaData).subscribe(
        response => {
          Swal.fire('Cita creada', 'La cita se ha creado con éxito', 'success');
          this.citaForm.reset();
          this.router.navigate(['/cliente/citas']); // Cambia esta ruta si es necesario
        },
        error => {
          console.error('Error al crear la cita:', error);

          // Verificar si el error contiene un mensaje específico
          let errorMessage = 'Error al crear la cita'; // Mensaje por defecto
          if (error.error && error.error.error) {
            // Si el backend devuelve un mensaje en error.error.error
            errorMessage = error.error.error; // Mostrar el mensaje de error específico
          } else if (error.error && typeof error.error === 'string') {
            // Si el backend devuelve un mensaje plano
            errorMessage = error.error; // Mostrar el mensaje plano
          }
      
          // Mostrar el mensaje en un SweetAlert
          Swal.fire('Error !!', errorMessage, 'error');
        }
      );
    } else {
      // Construcción de datos para cita no aleatoria
      citaData = {
        fecha: this.citaForm.value.fecha,
        horaInicio: this.citaForm.value.horaInicio,
        servicio: { servicioId: this.citaForm.value.servicio },
        cliente: { id: this.usuarioAutenticado.id },
        recurso: { recursoId: this.citaForm.value.recurso },
        empleado: this.mostrarEmpleados ? { id: this.citaForm.value.empleado } : null,
      };
  
      // Consumir servicio de crear cita
      this.citaService.crearCita(citaData).subscribe(
        response => {
          Swal.fire('Cita creada', 'La cita se ha creado con éxito', 'success');
          this.citaForm.reset();
          this.router.navigate(['/cliente/citas']); // Cambia esta ruta si es necesario
        },
        error => {
          console.error('Error al crear la cita:', error);

          // Verificar si el error contiene un mensaje específico
          let errorMessage = 'Error al crear la cita'; // Mensaje por defecto
          if (error.error && error.error.error) {
            // Si el backend devuelve un mensaje en error.error.error
            errorMessage = error.error.error; // Mostrar el mensaje de error específico
          } else if (error.error && typeof error.error === 'string') {
            // Si el backend devuelve un mensaje plano
            errorMessage = error.error; // Mostrar el mensaje plano
          }
      
          // Mostrar el mensaje en un SweetAlert
          Swal.fire('Error !!', errorMessage, 'error');
        }
      );
    }
  }

  
}
