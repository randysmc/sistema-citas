export interface ServiceReportDTO {
    nombre: string;       // El nombre del servicio o lo que estés reportando
    cantidad: number;     // La cantidad de veces que se usó o la cantidad asociada al servicio
    ingresos?: number;   // Si es un reporte de ingresos, puedes agregar este campo
  }