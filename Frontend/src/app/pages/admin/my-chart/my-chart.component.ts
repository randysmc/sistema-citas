import { Component } from '@angular/core';
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-my-chart',
  templateUrl: './my-chart.component.html',
  styleUrls: ['./my-chart.component.css']
})
export class MyChartComponent {
  chart: any;

  ngOnInit(): void {
    this.createChart();
  }

  createChart(): void {
    this.chart = new Chart('myChart', {
      type: 'bar', // Cambia a 'pie' para un gráfico de pastel
      data: {
        labels: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo'],
        datasets: [{
          label: 'Mi Primer Gráfico',
          data: [10, 20, 30, 40, 50],
          backgroundColor: 'rgba(75, 192, 192, 0.2)',
          borderColor: 'rgba(75, 192, 192, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }
}
