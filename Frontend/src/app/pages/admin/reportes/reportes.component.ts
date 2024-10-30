import { Component } from '@angular/core';
import { ReporteDTO } from 'src/app/models/reportedot.model';
import { ReporteService } from 'src/app/services/reporte.service';
@Component({
  selector: 'app-reportes',
  templateUrl: './reportes.component.html',
  styleUrls: ['./reportes.component.css']
})
export class ReportesComponent {

  reportes: ReporteDTO[] = [];

  constructor(private reporteService: ReporteService) { }

  ngOnInit(): void {
    this.obtenerCitasPorCliente();
  }

  obtenerCitasPorCliente(): void {
    this.reporteService.getCitasPorCliente().subscribe(data => {
      this.reportes = data;
      console.log(this.reportes);
    }, error => {
      console.error('Error al obtener citas por cliente', error);
    });
  }
}
