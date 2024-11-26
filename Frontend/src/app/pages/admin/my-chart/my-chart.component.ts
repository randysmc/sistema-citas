import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js/auto';
import { ReporteService } from 'src/app/services/reporte.service';
import { ServiceReportDTO } from 'src/app/models/service-report.dto';
import { UsuarioReporteDTO } from 'src/app/models/usuario-reporte.dto';

@Component({
  selector: 'app-my-chart',
  templateUrl: './my-chart.component.html',
  styleUrls: ['./my-chart.component.css']
})
export class MyChartComponent implements OnInit {
  chartServices: any; // Gráfica de servicios más usados
  chartRevenue: any;  // Gráfica de ingresos por mes
  chartTopClients: any;
  chartTopEmployes: any;
  serviceReports: ServiceReportDTO[] = [];
  revenueReports: ServiceReportDTO[] = [];
  topClients: UsuarioReporteDTO[] = [];
  topEmployes: UsuarioReporteDTO[] = [];

  // Variables para el mes y año seleccionados
  selectedMonth: number = 11; // Mes predeterminado (Noviembre)
  selectedYear: number = 2024; // Año predeterminado (2024)

  constructor(private reporteService: ReporteService) {}

  ngOnInit(): void {
    this.loadServiceUsageChart(); // Cargar gráfica de servicios más usados
    this.loadRevenueByMonthChart(this.selectedMonth, this.selectedYear); // Cargar gráfica de ingresos por mes
    this.loadTopClientsChart();
    this.loadTopEmployesChart();
  }

  // Cargar gráfica de servicios más usados
  loadServiceUsageChart(): void {
    this.reporteService.getMostUsedServices().subscribe((data: ServiceReportDTO[]) => {
      this.serviceReports = data;
      this.createServiceUsageChart();
    }, (error) => {
      console.error('Error al obtener los datos de servicios más usados:', error);
    });
  }

  createServiceUsageChart(): void {
    // Destruir el gráfico anterior si existe
    if (this.chartServices) {
      this.chartServices.destroy();
    }
  
    const labels = this.serviceReports.map(report => report.serviceName);
    const data = this.serviceReports.map(report => report.usageCount);
  
    this.chartServices = new Chart('chartServices', {
      type: 'pie',
      data: {
        labels: labels,
        datasets: [{
          label: 'Servicios más usados',
          data: data,
          backgroundColor: this.generateRandomColors(data.length),
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          title: {
            display: true,
            text: 'Servicios más utilizados'
          }
        }
      }
    });
  }
  

  // Cargar gráfica de ingresos por servicio y mes
  loadRevenueByMonthChart(month: number, year: number): void {
    console.log(`Cargando datos para mes ${month} y año ${year}`); // Depuración
    this.reporteService.getRevenueByServiceAndMonth(month, year).subscribe((data: ServiceReportDTO[]) => {
      this.revenueReports = data;
      this.createRevenueByMonthChart();
    }, (error) => {
      console.error('Error al obtener los ingresos por mes:', error);
    });
  }

  createRevenueByMonthChart(): void {
    // Destruir el gráfico anterior si existe
    if (this.chartRevenue) {
      this.chartRevenue.destroy();
    }
  
    const labels = this.revenueReports.map(report => report.serviceName);
    const data = this.revenueReports.map(report => report.usageCount);
  
    this.chartRevenue = new Chart('chartRevenue', {
      type: 'bar', // Gráfica de barras
      data: {
        labels: labels,
        datasets: [{
          label: 'Ingresos por servicio',
          data: data,
          backgroundColor: this.generateRandomColors(data.length),
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          title: {
            display: true,
            text: `Ingresos por mes: ${this.getMonthName(this.selectedMonth)} ${this.selectedYear}`
          }
        }
      }
    });
  }

  loadTopClientsChart(): void {
    this.reporteService.getTopClientsByCitas().subscribe((data: UsuarioReporteDTO[]) => {
      this.topClients = data;
      this.createTopClientsChart();
    }, (error) => {
      console.error('Error al obtener los datos de los clientes más frecuentes:', error);
    });
  }

  loadTopEmployesChart(): void {
    this.reporteService.getTopEmployeesByCitas().subscribe(
      (data: UsuarioReporteDTO[]) =>{
        this.topEmployes = data;
        this.createTopEmployesChart();
      }, (error) => {

      }
    )
  }


  createTopClientsChart(): void {
    const labels = this.topClients.map(client => `${client.nombre} ${client.apellido}`);
    const data = this.topClients.map(client => client.cantidad);

    this.chartTopClients?.destroy(); // Destruir gráfica previa, si existe
    this.chartTopClients = new Chart('chartTopClients', {
      type: 'bar',
      data: {
        labels: labels,
        datasets: [{
          label: 'Citas',
          data: data,
          backgroundColor: this.generateRandomColors(data.length),
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          title: {
            display: true,
            text: 'Clientes con más citas'
          }
        }
      }
    });
  }

  createTopEmployesChart(): void {
    const labels = this.topEmployes.map(employe => `${employe.nombre} ${employe.apellido}`);
    const data = this.topEmployes.map(employe => employe.cantidad);

    this.chartTopEmployes?.destroy(); // Destruir gráfica previa, si existe
    this.chartTopEmployes = new Chart('chartTopEmployes', {
      type: 'bar',
      data: {
        labels: labels,
        datasets: [{
          label: 'Citas realizadas',
          data: data,
          backgroundColor: this.generateRandomColors(data.length),
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          title: {
            display: true,
            text: 'Empleados con más citas realizadas'
          }
        }
      }
    });
  }
  

  // Obtener el nombre del mes a partir del número
  getMonthName(month: number): string {
    const months = [
      'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
      'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
    ];
    return months[month - 1];
  }

  // Generar colores aleatorios
  generateRandomColors(count: number): string[] {
    const colors = [];
    for (let i = 0; i < count; i++) {
      const randomColor = `rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, 0.5)`;
      colors.push(randomColor);
    }
    return colors;
  }
}
