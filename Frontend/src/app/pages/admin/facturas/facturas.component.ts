import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FacturaService } from 'src/app/services/factura.service';

@Component({
  selector: 'app-facturas',
  templateUrl: './facturas.component.html',
  styleUrls: ['./facturas.component.css']
})
export class FacturasComponent implements OnInit {

  facturas: any[] = []; // Para almacenar la lista de facturas

  constructor(private facturaService: FacturaService, private router: Router) {}

  ngOnInit() {
    this.obtenerFacturas(); // Obtener todas las facturas al iniciar
  }

  // Método para obtener todas las facturas
  obtenerFacturas() {
    this.facturaService.obtenerFacturas().subscribe(data => {
      this.facturas = data; // Almacenar las facturas recibidas
      console.log(this.facturas); // Para ver la lista de facturas en la consola
    }, error => {
      console.error('Error al obtener las facturas:', error); // Manejo de errores
    });
  }

    // Método para redirigir a la vista de detalle
    verDetalle(facturaId: number) {
      this.router.navigate(['/admin/facturas', facturaId]); // Navegar a la vista de detalle de la cita
    }
}
