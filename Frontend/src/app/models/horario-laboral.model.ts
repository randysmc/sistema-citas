// horario-laboral.model.ts
export interface HorarioLaboral {
    horarioLaboralId: number;
    dia: string; // Puedes usar un enum si lo deseas
    horaInicio: number[]; // [hora, minuto]
    horaFin: number[];
    tipoHorario: string;
}
