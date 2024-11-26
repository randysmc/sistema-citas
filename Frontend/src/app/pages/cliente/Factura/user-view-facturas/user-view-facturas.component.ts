import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FacturaService } from 'src/app/services/factura.service';
import { LoginService } from 'src/app/services/login.service'; // Asegúrate de importar el servicio



@Component({
  selector: 'app-user-view-facturas',
  templateUrl: './user-view-facturas.component.html',
  styleUrls: ['./user-view-facturas.component.css']
})
export class UserViewFacturasComponent {

  facturas: any[] = []; // Para almacenar la lista de facturas
  userId!: number; // Almacena el ID del usuario

  constructor(
    private facturaService: FacturaService,
    private router: Router,
    private loginService: LoginService // Inyecta el servicio de login
  ) {}

  ngOnInit() {
    this.loginService.getCurrentUser().subscribe(
      (user: any) => {
        this.userId = user.id; // Almacena el ID del usuario
        this.obtenerFacturas(); // Obtener las facturas después de obtener el usuario
      },
      (error) => {
        console.error('Error al obtener el usuario:', error);
      }
    );
  }

  // Método para obtener las facturas por usuario
  obtenerFacturas() {
    if (this.userId) {
      this.facturaService.obtenerFacturasPorUsuario(this.userId).subscribe(
        data => {
          this.facturas = data; // Almacenar las facturas recibidas
          console.log(this.facturas); // Para ver la lista de facturas en la consola
        },
        error => {
          console.error('Error al obtener las facturas:', error); // Manejo de errores
        }
      );
    }
  }

  // Método para redirigir a la vista de detalle
  verDetalle(facturaId: number) {
    this.router.navigate(['/cliente/facturas', facturaId]); // Navegar a la vista de detalle de la cita
  }
}
