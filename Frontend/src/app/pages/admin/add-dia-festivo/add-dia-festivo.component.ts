import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { DiasFestivosService } from 'src/app/services/dias-festivos.service';
import { DiaFestivo } from 'src/app/models/dia-festivo.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-dia-festivo',
  templateUrl: './add-dia-festivo.component.html',
  styleUrls: ['./add-dia-festivo.component.css']
})
export class AddDiaFestivoComponent {
  diaFestivo: DiaFestivo = {
    idDiaFestivo: 0, // Este valor no será enviado
    fecha: new Date(),
    descripcion: '',
    recurrente: false,
    anyo: new Date().getFullYear()
  };

  constructor(private diasFestivoService: DiasFestivosService, private router: Router) {}

  // Método para manejar el envío del formulario
  onSubmit() {
    // Asegúrate de establecer el año a partir de la fecha seleccionada
    this.diaFestivo.anyo = this.diaFestivo.fecha.getFullYear();

    // Validaciones simples
    if (!this.diaFestivo.descripcion) {
      Swal.fire('Error', 'La descripción es obligatoria', 'error');
      return;
    }

    // Agrega el día festivo usando el servicio
    this.diasFestivoService.agregarDiaFestivo(this.diaFestivo).subscribe(
      response => {
        Swal.fire('Éxito', 'Día festivo agregado correctamente', 'success');
        this.router.navigate(['/admin/calendario']); // Redirige a la lista de días festivos
      },
      error => {
        console.error('Error al agregar el día festivo', error);
        Swal.fire('Error', 'Hubo un problema al agregar el día festivo', 'error');
      }
    );
  }
}
