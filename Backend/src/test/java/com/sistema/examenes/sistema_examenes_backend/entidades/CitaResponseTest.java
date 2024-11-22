package com.sistema.examenes.sistema_examenes_backend.entidades;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class CitaResponseTest {

    @Test
    void testSettersAndGetters() {
        // Arrange
        Cita cita = new Cita(); // Suponiendo que Cita tiene un constructor sin argumentos
        String mensaje = "Cita creada exitosamente";
        CitaResponse citaResponse = new CitaResponse(mensaje, cita);

        // Act
        citaResponse.setMensaje("Cita actualizada");
        citaResponse.setCita(null);

        // Assert
        assertEquals("Cita actualizada", citaResponse.getMensaje(), "El mensaje debería coincidir con el valor establecido");
        assertNull(citaResponse.getCita(), "La cita debería ser null tras establecerlo");
    }

    @Test
    void testConstructor() {
        // Arrange
        Cita cita = new Cita(); // Suponiendo que Cita tiene un constructor sin argumentos
        String mensaje = "Cita creada exitosamente";

        // Act
        CitaResponse citaResponse = new CitaResponse(mensaje, cita);

        // Assert
        assertEquals(mensaje, citaResponse.getMensaje(), "El mensaje debería coincidir con el proporcionado al constructor");
        assertEquals(cita, citaResponse.getCita(), "La cita debería coincidir con el objeto proporcionado al constructor");
    }
}
