package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import com.sistema.examenes.sistema_examenes_backend.Enums.TipoRecurso;
import com.sistema.examenes.sistema_examenes_backend.entidades.*;
import com.sistema.examenes.sistema_examenes_backend.repositorios.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
@Transactional
public class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    private Usuario usuarioGlobal;
    private Usuario empleadoGlobal;
    private Recurso recursoGlobal;
    private Servicio servicioGlobal;
    private Cita citaGlobal; // Cita global para la reserva

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada prueba
        citaRepository.deleteAll();
        usuarioRepository.deleteAll();
        servicioRepository.deleteAll();
        recursoRepository.deleteAll();
        reservaRepository.deleteAll(); // Limpiar reservas también

        // Crear el usuario cliente
        usuarioGlobal = new Usuario();
        usuarioGlobal.setNombre("Ringo");
        usuarioGlobal.setApellido("Sum");
        usuarioGlobal.setUsername("nachito");
        usuarioGlobal.setPassword("password");
        usuarioGlobal.setEmail("nacho@gmail.com");
        usuarioGlobal.setTelefono("7754");
        usuarioGlobal.setNit("465654");
        usuarioGlobal.setCui("654");
        usuarioGlobal.setPerfil("foto.png");
        usuarioRepository.save(usuarioGlobal);

        // Crear el usuario empleado
        empleadoGlobal = new Usuario();
        empleadoGlobal.setNombre("Ana");
        empleadoGlobal.setApellido("Gómez");
        empleadoGlobal.setUsername("anagomez");
        empleadoGlobal.setPassword("password");
        empleadoGlobal.setEmail("ana.gomez@example.com");
        empleadoGlobal.setNit("789987987");
        empleadoGlobal.setCui("44565465");
        usuarioRepository.save(empleadoGlobal);

        // Crear el recurso
        recursoGlobal = new Recurso();
        recursoGlobal.setNombre("Recurso de Ejemplo");
        recursoGlobal.setDescripcion("Descripción del recurso de ejemplo");
        recursoGlobal.setDisponible(true);
        recursoGlobal.setTipo(TipoRecurso.INSTALACION); // Asegúrate de que este tipo esté definido
        recursoRepository.save(recursoGlobal);

        // Crear el servicio
        servicioGlobal = new Servicio();
        servicioGlobal.setNombre("Servicio de Ejemplo");
        servicioGlobal.setDescripcion("Descripción del servicio de ejemplo");
        servicioGlobal.setDuracionServicio(60);
        servicioGlobal.setPrecio(BigDecimal.valueOf(100.0));
        servicioGlobal.setDisponible(true);
        servicioRepository.save(servicioGlobal);

        // Crear la cita global
        citaGlobal = new Cita();
        citaGlobal.setFecha(LocalDate.of(2024, 10, 29));
        citaGlobal.setHoraInicio(LocalTime.of(10, 0));
        citaGlobal.setHoraFin(LocalTime.of(11, 0));
        citaGlobal.setEstado(EstadoCita.AGENDADA);
        citaGlobal.setCliente(usuarioGlobal);
        citaGlobal.setEmpleado(empleadoGlobal);
        citaGlobal.setRecurso(recursoGlobal);
        citaGlobal.setServicio(servicioGlobal);
        citaGlobal = citaRepository.save(citaGlobal); // Guardar la cita
    }

    @Test
    public void testCrearReserva() {
        // Crear y guardar una reserva
        Reserva reserva = crearReserva();
        Reserva reservaGuardada = reservaRepository.save(reserva);

        // Verificar que la reserva se ha guardado correctamente
        assertThat(reservaGuardada).isNotNull();
        assertThat(reservaGuardada.getCita()).isEqualTo(citaGlobal);
    }

    @Test
    public void testObtenerReservasPorEmpleadoIdYFecha() {
        Reserva reserva = crearReserva();
        reservaRepository.save(reserva);

        List<Reserva> reservas = reservaRepository.findByEmpleadoIdAndFecha(empleadoGlobal.getId(), LocalDate.of(2024, 10, 29));

        assertThat(reservas).isNotEmpty();
        assertThat(reservas.get(0).getEmpleado()).isEqualTo(empleadoGlobal);
    }

    @Test
    public void testObtenerTodasLasReservas() {
        Reserva reserva1 = crearReserva();
        Reserva reserva2 = crearReserva();
        reservaRepository.save(reserva1);
        reservaRepository.save(reserva2);

        List<Reserva> reservas = reservaRepository.findAll();

        assertThat(reservas).hasSize(2);
    }

    @Test
    public void testActualizarReserva() {
        Reserva reserva = crearReserva();
        Reserva reservaGuardada = reservaRepository.save(reserva);

        // Actualizar la reserva
        reservaGuardada.setActiva(false);
        Reserva reservaActualizada = reservaRepository.save(reservaGuardada);

        // Verificar que la reserva se ha actualizado
        assertThat(reservaActualizada.getActiva()).isFalse();
    }

    @Test
    public void testEliminarReserva() {
        Reserva reserva = crearReserva();
        Reserva reservaGuardada = reservaRepository.save(reserva);

        // Eliminar la reserva
        reservaRepository.delete(reservaGuardada);

        // Verificar que la reserva ha sido eliminada
        List<Reserva> reservas = reservaRepository.findAll();
        assertThat(reservas).isEmpty();
    }

    @Test
    public void testObtenerReservasPorRecursoYFecha() {
        Reserva reserva = crearReserva();
        reservaRepository.save(reserva);

        List<Reserva> reservas = reservaRepository.findByRecursoAndFecha(recursoGlobal, LocalDate.of(2024, 10, 29));

        assertThat(reservas).isNotEmpty();
        assertThat(reservas.get(0).getRecurso()).isEqualTo(recursoGlobal);
    }

    @Test
    public void testObtenerReservasPorEmpleadoYFecha() {
        Reserva reserva = crearReserva();
        reservaRepository.save(reserva);

        List<Reserva> reservas = reservaRepository.findByEmpleadoAndFecha(empleadoGlobal, LocalDate.of(2024, 10, 29));

        assertThat(reservas).isNotEmpty();
        assertThat(reservas.get(0).getEmpleado()).isEqualTo(empleadoGlobal);
    }

    @Test
    public void testObtenerReservasPorEmpleadoId() {
        Reserva reserva = crearReserva();
        reservaRepository.save(reserva);

        List<Reserva> reservas = reservaRepository.findByEmpleadoId(empleadoGlobal.getId());

        assertThat(reservas).isNotEmpty();
        assertThat(reservas.get(0).getEmpleado()).isEqualTo(empleadoGlobal);
    }

    @Test
    public void testObtenerReservasActivas() {
        Reserva reservaActiva = crearReserva();
        reservaActiva.setActiva(true);
        reservaRepository.save(reservaActiva);

        Reserva reservaInactiva = crearReserva();
        reservaInactiva.setActiva(false);
        reservaRepository.save(reservaInactiva);

        List<Reserva> reservasActivas = reservaRepository.findByActivaTrue();

        assertThat(reservasActivas).hasSize(1);
        assertThat(reservasActivas.get(0).getActiva()).isTrue();
    }

    @Test
    public void testObtenerReservaPorIdCita() {
        Reserva reserva = crearReserva();
        reservaRepository.save(reserva);

        Reserva reservaEncontrada = reservaRepository.findByCitaIdCita(citaGlobal.getIdCita());

        assertThat(reservaEncontrada).isNotNull();
        assertThat(reservaEncontrada.getCita()).isEqualTo(citaGlobal);
    }



    private Reserva crearReserva() {
        Reserva reserva = new Reserva();
        reserva.setFecha(LocalDate.of(2024, 10, 29));
        reserva.setHoraInicio(LocalTime.of(10, 0));
        reserva.setHoraFin(LocalTime.of(11, 0));
        reserva.setActiva(true);
        reserva.setCita(citaGlobal);
        reserva.setRecurso(recursoGlobal);
        reserva.setEmpleado(empleadoGlobal);
        reserva.setCliente(usuarioGlobal);
        return reserva;
    }
}
