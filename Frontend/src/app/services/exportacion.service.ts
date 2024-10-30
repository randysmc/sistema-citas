import { Injectable } from '@angular/core';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import * as XLSX from 'xlsx';

@Injectable({
  providedIn: 'root'
})
export class ExportacionService {

  constructor() { }

  // Función para descargar como PDF

// Función para descargar como PDF
downloadFacturaPDF(negocio: any, elementId: string, filename: string) {
  const data = document.getElementById(elementId);
  if (data) {
    html2canvas(data).then(canvas => {
      const imgData = canvas.toDataURL('image/png');
      const pdf = new jsPDF();

      // Agregar logo del negocio
      const logoImg = negocio.fotoPerfilUrl; // Usa la URL de la imagen que has generado
      pdf.addImage(logoImg, 'JPEG', 10, 10, 50, 50); // Ajusta la posición y tamaño del logo

      // Agregar texto del negocio
      pdf.setFontSize(18);
      pdf.text(String(negocio.nombre), 10, 70); // Asegúrate de que sea un string
      pdf.setFontSize(12);
      pdf.text(String(negocio.direccion), 10, 80); // Asegúrate de que sea un string
      pdf.text(`Teléfono: ${String(negocio.telefono)}`, 10, 90); // Asegúrate de que sea un string
      pdf.text(`Email: ${String(negocio.email)}`, 10, 100); // Asegúrate de que sea un string
      pdf.text(String(negocio.descripcion), 10, 110); // Asegúrate de que sea un string
      pdf.text(`Slogan: ${String(negocio.slogan)}`, 10, 120); // Asegúrate de que sea un string

      // Agregar mensaje final
      pdf.text('Gracias por utilizar los servicios de ' + String(negocio.nombre), 10, 140); // Asegúrate de que sea un string
      pdf.text('Esperamos que vuelva pronto.', 10, 150);
      pdf.text('Sujeto a pagos trimestrales.', 10, 160);

      // Guardar el PDF
      pdf.save(filename);
    });
  }
}


  

  // Función para descargar como Excel
  downloadAsExcel(data: any[], filename: string) {
    const worksheet = XLSX.utils.json_to_sheet(data);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Datos');
    XLSX.writeFile(workbook, filename);
  }

  // Función para descargar como imagen
  downloadAsImage(elementId: string, filename: string) {
    const data = document.getElementById(elementId);
    if (data) {
      html2canvas(data).then(canvas => {
        const imgData = canvas.toDataURL('image/png');
        const link = document.createElement('a');
        link.href = imgData;
        link.download = filename;
        link.click();
      });
    }
  }
}
