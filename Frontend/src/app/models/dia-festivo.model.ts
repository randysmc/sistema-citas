// dia-festivo.model.ts
export interface DiaFestivo {
    idDiaFestivo: number;
    fecha: Date; // Cambiado a Date
    descripcion: string;
    recurrente: boolean;
    anyo: number;
  }
  