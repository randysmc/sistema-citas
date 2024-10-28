package com.sistema.examenes.sistema_examenes_backend.configuraciones;

import com.sistema.examenes.sistema_examenes_backend.Enums.DiaSemana;

import java.time.DayOfWeek;

public class DiaSemanaConverter {

    public static DiaSemana convertirADiaSemana(DayOfWeek dayOfWeek){
        switch (dayOfWeek){
            case MONDAY: return DiaSemana.LUNES;
            case TUESDAY: return DiaSemana.MARTES;
            case WEDNESDAY: return DiaSemana.MIERCOLES;
            case THURSDAY: return DiaSemana.JUEVES;
            case FRIDAY: return DiaSemana.VIERNES;
            case SATURDAY: return DiaSemana.SABADO;
            case SUNDAY: return DiaSemana.DOMINGO;
            default: throw new IllegalArgumentException("Día no válido: " + dayOfWeek);
        }
    }
}
