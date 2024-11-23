import { Component, OnInit } from '@angular/core';
import { NegocioService } from 'src/app/services/negocio.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  negocio: any;

  constructor(
    private negocioService : NegocioService
  ){}

  ngOnInit(): void {
    this.cargarNegocio();
  }

  private cargarNegocio() {
    this.negocioService.obtenerNegocioPorId(1).subscribe(
      (data: any) => {
        this.negocio = data;
      },
      error => {
        console.error('Error al obtener el negocio', error);
      }
    );
  }

}
