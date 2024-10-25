// dia-festivo.model.ts
export interface DiaFestivo {
    idDiaFestivo: number;
    fecha: number[]; // [año, mes, día]
    descripcion: string;
    recurrente: boolean;
    anyo: number;
  }
  