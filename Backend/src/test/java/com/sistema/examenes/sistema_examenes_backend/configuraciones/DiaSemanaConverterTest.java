package com.sistema.examenes.sistema_examenes_backend.configuraciones;

import com.sistema.examenes.sistema_examenes_backend.Enums.DiaSemana;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.*;

class DiaSemanaConverterTest {

    @Test
    void convertirADiaSemana_ShouldReturnLUNES_WhenMondayProvided() {
        // Act
        DiaSemana result = DiaSemanaConverter.convertirADiaSemana(DayOfWeek.MONDAY);

        // Assert
        assertEquals(DiaSemana.LUNES, result);
    }

    @Test
    void convertirADiaSemana_ShouldReturnMARTES_WhenTuesdayProvided() {
        // Act
        DiaSemana result = DiaSemanaConverter.convertirADiaSemana(DayOfWeek.TUESDAY);

        // Assert
        assertEquals(DiaSemana.MARTES, result);
    }

    @Test
    void convertirADiaSemana_ShouldReturnMIERCOLES_WhenWednesdayProvided() {
        // Act
        DiaSemana result = DiaSemanaConverter.convertirADiaSemana(DayOfWeek.WEDNESDAY);

        // Assert
        assertEquals(DiaSemana.MIERCOLES, result);
    }

    @Test
    void convertirADiaSemana_ShouldReturnJUEVES_WhenThursdayProvided() {
        // Act
        DiaSemana result = DiaSemanaConverter.convertirADiaSemana(DayOfWeek.THURSDAY);

        // Assert
        assertEquals(DiaSemana.JUEVES, result);
    }

    @Test
    void convertirADiaSemana_ShouldReturnVIERNES_WhenFridayProvided() {
        // Act
        DiaSemana result = DiaSemanaConverter.convertirADiaSemana(DayOfWeek.FRIDAY);

        // Assert
        assertEquals(DiaSemana.VIERNES, result);
    }

    @Test
    void convertirADiaSemana_ShouldReturnSABADO_WhenSaturdayProvided() {
        // Act
        DiaSemana result = DiaSemanaConverter.convertirADiaSemana(DayOfWeek.SATURDAY);

        // Assert
        assertEquals(DiaSemana.SABADO, result);
    }

    @Test
    void convertirADiaSemana_ShouldReturnDOMINGO_WhenSundayProvided() {
        // Act
        DiaSemana result = DiaSemanaConverter.convertirADiaSemana(DayOfWeek.SUNDAY);

        // Assert
        assertEquals(DiaSemana.DOMINGO, result);
    }


}
