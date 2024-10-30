import { Component } from '@angular/core';
import { ReporteDTO } from 'src/app/models/reportedot.model';
import { NegocioService } from 'src/app/services/negocio.service';
import { ReporteService } from 'src/app/services/reporte.service';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';

@Component({
  selector: 'app-reportes',
  templateUrl: './reportes.component.html',
  styleUrls: ['./reportes.component.css'],
})
export class ReportesComponent {
  reportes: ReporteDTO[] = [];
  negocio: any;
  selectedReporte: string = 'citasPorCliente'; // Tipo de reporte seleccionado
  reportesHorarios: { [key: string]: number } = {};
  reportesFrecuencia: { [key: string]: number } = {};

  constructor(private reporteService: ReporteService, private negocioService: NegocioService) {}

  ngOnInit(): void {
    this.obtenerCitasPorCliente(); // Cargar datos por defecto
    this.cargarNegocio();
  }


  private cargarNegocio() {
    this.negocioService.obtenerNegocioPorId(1).subscribe(
      (data: any) => {
        this.negocio = data;
        this.negocio.fotoPerfilUrl = this.buildImageUrl(data);
      },
      error => {
        console.error('Error al obtener el negocio', error);
      }
    );
  }

  private buildImageUrl(negocio: any): string {
    const folder = 'negocio';
    const filename = negocio.fotoPerfil.split('/').pop();
    return `http://localhost:8080/uploads/${folder}/${filename}`;
  }


  // Método para obtener citas por cliente
  obtenerCitasPorCliente(): void {
    this.reporteService.getCitasPorCliente().subscribe(
      (data) => {
        this.reportes = data;
        console.log(this.reportes);
      },
      (error) => {
        console.error('Error al obtener citas por cliente', error);
      }
    );
  }

  // Método para obtener usuarios con más citas agendadas
  obtenerUsuarioConMasCitasAgendadas(): void {
    this.reporteService.getUsuarioConMasCitasAgendadas().subscribe(
      (data) => {
        this.reportes = data;
        console.log(this.reportes);
      },
      (error) => {
        console.error(
          'Error al obtener usuarios con más citas agendadas',
          error
        );
      }
    );
  }

  obtenerUsuarioConMasCitasCanceladas(): void {
    this.reporteService.getUsuarioConMasCitasCanceladas().subscribe(
      (data) => {
        this.reportes = data;
        console.log(this.reportes);
      },
      (error) => {
        console.error(
          'Error al obtener usuario con más citas canceladas',
          error
        );
      }
    );
  }

  obtenerHorariosMasSolicitados(): void {
    this.reporteService.obtenerHorariosMasSolicitados().subscribe(
      (data) => {
        this.reportesHorarios = data.detalles; // Asignamos solo los detalles
        console.log(this.reportesHorarios);
      },
      (error) => {
        console.error('Error al obtener horarios más solicitados', error);
      }
    );
  }

  obtenerFrecuenciaUsoDiaSemana(): void {
    this.reporteService.obtenerFrecuenciaUsoPorDiaSemana().subscribe(
      (data) => {
        this.reportesFrecuencia = data.detalles; // Asignamos solo los detalles
      },
      (error) => {
        console.error(
          'Error al obtener frecuencia de uso por día de la semana',
          error
        );
      }
    );
  }

  onReporteChange(event: Event): void {
    const selectElement = event.target as HTMLSelectElement; // Asegúrate de que sea un HTMLSelectElement
    this.selectedReporte = selectElement.value;

    if (this.selectedReporte === 'citasPorCliente') {
      this.obtenerCitasPorCliente();
    } else if (this.selectedReporte === 'usuariosMasAgendados') {
      this.obtenerUsuarioConMasCitasAgendadas();
    } else if (this.selectedReporte === 'usuarioMasCanceladas') {
      this.obtenerUsuarioConMasCitasCanceladas();
    } else if (this.selectedReporte === 'horariosMasSolicitados') {
      this.obtenerHorariosMasSolicitados();
    } else if (this.selectedReporte === 'frecuenciaUsoDiasSemana') {
      this.obtenerFrecuenciaUsoDiaSemana();
    }
  }

  exportarAPDF(): void {
    const element = document.getElementById('reporte'); // Cambié 'factura-element' a 'reporte'
    if (element) {
      html2canvas(element, { useCORS: true }).then((canvas) => {
        const imgData = canvas.toDataURL('image/png');
        const pdf = new jsPDF();
        const imgWidth = 190; // Ajusta el ancho de la imagen según sea necesario
        const pageHeight = pdf.internal.pageSize.height;
        const imgHeight = (canvas.height * imgWidth) / canvas.width;
        let heightLeft = imgHeight;

        let position = 0;

        pdf.addImage(imgData, 'PNG', 10, position, imgWidth, imgHeight);
        heightLeft -= pageHeight;

        while (heightLeft >= 0) {
          position = heightLeft - imgHeight;
          pdf.addPage();
          pdf.addImage(imgData, 'PNG', 10, position, imgWidth, imgHeight);
          heightLeft -= pageHeight;
        }

        pdf.save('reporte.pdf'); // Cambié 'factura.pdf' a 'reporte.pdf'
      });
    }
  }

  descargarImagen() {
    const element = document.getElementById('reporte');
    if (element) {
      html2canvas(element, { useCORS: true }).then((canvas) => {
        const imgData = canvas.toDataURL('image/png');
        const link = document.createElement('a');
        link.href = imgData;
        link.download = 'factura.png';
        link.click();
      });
    }
  }
}
