package com.sistema.examenes.sistema_examenes_backend.entidades;

import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class CitaTest {

    @Test
    void testConstructorAndGetters() {
        // given
        Long idCita = 1L;
        LocalDate fecha = LocalDate.of(2024, 11, 21);
        LocalTime horaInicio = LocalTime.of(10, 0);
        LocalTime horaFin = LocalTime.of(11, 0);
        EstadoCita estado = EstadoCita.CONFIRMADA;
        Recurso recurso = new Recurso(); // Mock or real object
        Servicio servicio = new Servicio(); // Mock or real object
        Usuario cliente = new Usuario(); // Mock or real object
        Usuario empleado = new Usuario(); // Mock or real object

        // when
        Cita cita = new Cita(idCita, fecha, horaInicio, horaFin, estado, recurso, servicio, cliente, empleado);

        // then
        assertEquals(idCita, cita.getIdCita());
        assertEquals(fecha, cita.getFecha());
        assertEquals(horaInicio, cita.getHoraInicio());
        assertEquals(horaFin, cita.getHoraFin());
        assertEquals(estado, cita.getEstado());
        assertEquals(recurso, cita.getRecurso());
        assertEquals(servicio, cita.getServicio());
        assertEquals(cliente, cita.getCliente());
        assertEquals(empleado, cita.getEmpleado());
    }

    @Test
    void testSetters() {
        // given
        Cita cita = new Cita();
        Long idCita = 2L;
        LocalDate fecha = LocalDate.of(2024, 12, 1);
        LocalTime horaInicio = LocalTime.of(14, 0);
        LocalTime horaFin = LocalTime.of(15, 0);
        EstadoCita estado = EstadoCita.AGENDADA;
        Recurso recurso = new Recurso();
        Servicio servicio = new Servicio();
        Usuario cliente = new Usuario();
        Usuario empleado = new Usuario();

        // when
        cita.setIdCita(idCita);
        cita.setFecha(fecha);
        cita.setHoraInicio(horaInicio);
        cita.setHoraFin(horaFin);
        cita.setEstado(estado);
        cita.setRecurso(recurso);
        cita.setServicio(servicio);
        cita.setCliente(cliente);
        cita.setEmpleado(empleado);

        // then
        assertEquals(idCita, cita.getIdCita());
        assertEquals(fecha, cita.getFecha());
        assertEquals(horaInicio, cita.getHoraInicio());
        assertEquals(horaFin, cita.getHoraFin());
        assertEquals(estado, cita.getEstado());
        assertEquals(recurso, cita.getRecurso());
        assertEquals(servicio, cita.getServicio());
        assertEquals(cliente, cita.getCliente());
        assertEquals(empleado, cita.getEmpleado());
    }

    @Test
    void testDefaultConstructor() {
        // when
        Cita cita = new Cita();

        // then
        assertNull(cita.getIdCita(), "El ID debería ser null por defecto");
        assertNull(cita.getFecha(), "La fecha debería ser null por defecto");
        assertNull(cita.getHoraInicio(), "La hora de inicio debería ser null por defecto");
        assertNull(cita.getHoraFin(), "La hora de fin debería ser null por defecto");
        assertNull(cita.getEstado(), "El estado debería ser null por defecto");
        assertNull(cita.getRecurso(), "El recurso debería ser null por defecto");
        assertNull(cita.getServicio(), "El servicio debería ser null por defecto");
        assertNull(cita.getCliente(), "El cliente debería ser null por defecto");
        assertNull(cita.getEmpleado(), "El empleado debería ser null por defecto");
    }
}
