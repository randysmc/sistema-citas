import { Component, OnInit } from '@angular/core';
import { NegocioService } from 'src/app/services/negocio.service';
import { ExportacionService } from 'src/app/services/exportacion.service';
import { FacturaService } from 'src/app/services/factura.service'; // Asegúrate de tener este servicio
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';


@Component({
  selector: 'app-user-view-facturas-detail',
  templateUrl: './user-view-facturas-detail.component.html',
  styleUrls: ['./user-view-facturas-detail.component.css']
})
export class UserViewFacturasDetailComponent {
  negocio: any;
  factura: any; // Datos de la factura

  constructor(
    private negocioService: NegocioService,
    private downloadService: ExportacionService,
    private facturaService: FacturaService // Servicio para obtener la factura
  ) {}

  ngOnInit() {
    this.cargarNegocio();
    this.cargarFactura(1); // Reemplaza 1 por el ID de la factura que necesitas
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

  private cargarFactura(facturaId: number) {
    this.facturaService.obtenerFacturaPorId(facturaId).subscribe(
      (data: any) => {
        this.factura = data;
      },
      error => {
        console.error('Error al obtener la factura', error);
      }
    );
  }

  private buildImageUrl(negocio: any): string {
    const folder = 'negocio';
    const filename = negocio.fotoPerfil.split('/').pop();
    return `http://localhost:8080/uploads/${folder}/${filename}`;
  }

  /*descargarPDF() {
    this.downloadService.downloadFacturaPDF(this.negocio, 'factura-element', 'factura.pdf');
  }*/


    descargarPDF() {
      const element = document.getElementById('factura-element');
      if (element) {
        html2canvas(element, { useCORS: true }).then((canvas) => {
          const imgData = canvas.toDataURL('image/png');
          const pdf = new jsPDF();
          const imgWidth = 190; 
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
    
          pdf.save('factura.pdf');
        });
      }
    }
    


  descargarImagen() {
    const element = document.getElementById('factura-element');
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
