package com.sistema.examenes.sistema_examenes_backend.service;

import com.sistema.examenes.sistema_examenes_backend.Enums.DiaSemana;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoComprobante;
import com.sistema.examenes.sistema_examenes_backend.Enums.TipoRecurso;
import com.sistema.examenes.sistema_examenes_backend.entidades.*;
import com.sistema.examenes.sistema_examenes_backend.repositorios.*;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.ReservaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ReservaServiceTest {

    @Mock
    private CitaRepository citaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RecursoRepository recursoRepository;

    @Mock
    private ServicioRepository servicioRepository;

    @Mock
    private DiaFestivoRepository diaFestivoRepository;

    @Mock
    private HorarioLaboralRepository horarioLaboralRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ComprobanteRepository comprobanteRepository;

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private ReservaServiceImpl reservaService;


    private Cita citaGlobal;
    private Usuario usuarioGlobal;
    private Recurso recursoGlobal;
    private Servicio servicioGlobal;
    private Usuario empleadoGlobal;
    private DiaFestivo diaFestivoGlobal;
    private HorarioLaboral horarioLunes;
    private Reserva reservaGlobal;
    private Comprobante comprobanteGlobal;

    private Set<UsuarioRol> usuarioRoles;

    @BeforeEach
    public void setup() {


        usuarioGlobal = new Usuario();
        usuarioGlobal.setId(1L);
        usuarioGlobal.setUsername("randysmc");


        // Inicializar roles del cliente
        Set<UsuarioRol> rolesCliente = new HashSet<>();
        Rol rolCliente = new Rol();
        rolCliente.setRolNombre("CLIENTE");
        UsuarioRol usuarioRolCliente = new UsuarioRol();
        usuarioRolCliente.setRol(rolCliente);
        usuarioRolCliente.setUsuario(usuarioGlobal);
        rolesCliente.add(usuarioRolCliente);

        // Asignar roles al cliente y guardar
        usuarioGlobal.setUsuarioRoles(rolesCliente);
        usuarioRepository.save(usuarioGlobal);

        // Crear el usuario empleado
        empleadoGlobal = new Usuario();
        empleadoGlobal.setId(2L);
        empleadoGlobal.setUsername("critaljyr");
        empleadoGlobal.setEnabled(true);


        // Inicializar roles del empleado
        Set<UsuarioRol> rolesEmpleado = new HashSet<>();
        Rol rolEmpleado = new Rol();
        rolEmpleado.setRolNombre("EMPLEADO");
        UsuarioRol usuarioRolEmpleado = new UsuarioRol();
        usuarioRolEmpleado.setRol(rolEmpleado);
        usuarioRolEmpleado.setUsuario(empleadoGlobal);
        rolesEmpleado.add(usuarioRolEmpleado);

        // Asignar roles al empleado y guardar
        empleadoGlobal.setUsuarioRoles(rolesEmpleado);
        usuarioRepository.save(empleadoGlobal);

        // Crear el recurso
        recursoGlobal = new Recurso();
        recursoGlobal.setNombre("Recurso de Ejemplo");
        recursoGlobal.setDescripcion("Descripción del recurso de ejemplo");
        recursoGlobal.setDisponible(true);
        recursoGlobal.setTipo(TipoRecurso.INSTALACION);
        recursoRepository.save(recursoGlobal);

        // Crear el servicio
        servicioGlobal = new Servicio();
        servicioGlobal.setNombre("Servicio de Ejemplo");
        servicioGlobal.setDescripcion("Descripción del servicio de ejemplo");
        servicioGlobal.setTipo(TipoRecurso.INSTALACION);
        servicioGlobal.setDuracionServicio(60);
        servicioGlobal.setPrecio(BigDecimal.valueOf(100.0));
        servicioGlobal.setDisponible(true);
        servicioRepository.save(servicioGlobal);


        diaFestivoGlobal = new DiaFestivo();
        diaFestivoGlobal.setFecha(LocalDate.of(2024, 12, 25));
        diaFestivoGlobal.setDescripcion("Navidad");
        diaFestivoGlobal.setRecurrente(true);
        diaFestivoGlobal.setAnyo(2024);
        diaFestivoRepository.save(diaFestivoGlobal);

        horarioLunes = new HorarioLaboral();
        horarioLunes.setDia(DiaSemana.LUNES);
        horarioLunes.setHoraInicio(LocalTime.of(8, 0));
        horarioLunes.setHoraFin(LocalTime.of(17, 0));
        horarioLunes.setTipoHorario("Regular");
        horarioLaboralRepository.save(horarioLunes);

        // Crear la cita global
        citaGlobal = new Cita();
        citaGlobal.setIdCita(1L);
        citaGlobal.setFecha(LocalDate.of(2024, 11, 29));
        citaGlobal.setHoraInicio(LocalTime.of(11, 0));
        //citaGlobal.setHoraFin(LocalTime.of(11, 0));
        //citaGlobal.setEstado(EstadoCita.AGENDADA);
        citaGlobal.setCliente(usuarioGlobal);
        citaGlobal.setEmpleado(empleadoGlobal);
        citaGlobal.setRecurso(recursoGlobal);
        citaGlobal.setServicio(servicioGlobal);
        //citaRepository.save(citaGlobal);


        reservaGlobal = new Reserva();
        reservaGlobal.setFecha(LocalDate.of(2024, 11, 29));
        reservaGlobal.setHoraInicio(LocalTime.of(10, 0));
        reservaGlobal.setHoraFin(LocalTime.of(11, 0));
        reservaGlobal.setActiva(true);
        reservaGlobal.setCita(citaGlobal);
        reservaGlobal.setRecurso(recursoGlobal);
        reservaGlobal.setEmpleado(empleadoGlobal);
        reservaGlobal.setCliente(usuarioGlobal);
        //reservaRepository.save(reservaGlobal);

        comprobanteGlobal = new Comprobante();
        comprobanteGlobal.setFecha(LocalDate.now());
        comprobanteGlobal.setHoraInicio(LocalTime.now());
        comprobanteGlobal.setEstadoComprobante(EstadoComprobante.AGENDADA); // Como en el método
        comprobanteGlobal.setCliente(usuarioGlobal);
        comprobanteGlobal.setCita(citaGlobal);
        comprobanteGlobal.setDescripcion("Descripción de la reserva para el cliente.");
        //comprobanteRepository.save(comprobanteGlobal);

    }

    @Test
    @DisplayName("Debería crear una nueva reserva correctamente")
    public void testCrearReserva() {
        // given que
        given(reservaRepository.save(any(Reserva.class))).willReturn(reservaGlobal);

        // when
        Reserva nuevaReserva = reservaService.crearReserva(reservaGlobal);

        // then
        assertThat(nuevaReserva).isNotNull();
        assertThat(nuevaReserva.getFecha()).isEqualTo(reservaGlobal.getFecha());
        assertThat(nuevaReserva.getHoraInicio()).isEqualTo(reservaGlobal.getHoraInicio());
        assertThat(nuevaReserva.getCliente().getId()).isEqualTo(usuarioGlobal.getId());
        assertThat(nuevaReserva.getEmpleado().getId()).isEqualTo(empleadoGlobal.getId());
        verify(reservaRepository, times(1)).save(reservaGlobal);
    }

    @Test
    @DisplayName("Debería obtener todas las reservas activas")
    public void testObtenerReservasActivas() {
        // Dado que
        given(reservaRepository.findByActivaTrue()).willReturn(List.of(reservaGlobal));

        // Cuando
        List<Reserva> reservasActivas = reservaService.obtenerReservasActivas();

        // Entonces
        assertThat(reservasActivas).isNotEmpty();
        assertThat(reservasActivas.get(0).getActiva()).isTrue();
        verify(reservaRepository, times(1)).findByActivaTrue();
    }


   /* @Test
    @DisplayName("Debería cancelar una reserva existente")
    public void testCancelarReserva() {
        // given
        Long reservaId = 1L;
        reservaGlobal.setReservaId(reservaId); // Asignamos un ID válido a la reserva global.

        // Imprimir la reservaGlobal para asegurarnos de que está bien configurada
        System.out.println("ReservaGlobal antes del test: " + reservaGlobal.getReservaId() + ", Activa: " + reservaGlobal.getActiva());

        // Mock de la reserva
        given(reservaRepository.findById(reservaId)).willReturn(Optional.of(reservaGlobal));

        // when
        Reserva reservaCancelada = reservaService.cancelarReserva(reservaId);

        // Imprimir la reserva cancelada para ver si es la esperada
        System.out.println("Reserva cancelada: " + reservaCancelada.getReservaId() + ", Activa: " + reservaCancelada.getActiva());

        // then
        assertThat(reservaCancelada).isNotNull(); // Asegúrate de que la reserva no sea null.
        assertThat(reservaCancelada.getActiva()).isFalse(); // Verifica que la reserva esté inactiva.

        // Verifica que se haya llamado a save con la reserva global
        verify(reservaRepository, times(1)).save(reservaGlobal);

        // Imprimir el estado de la reserva global después de la operación
        System.out.println("Estado final de la reservaGlobal: " + reservaGlobal.getReservaId() + ", Activa: " + reservaGlobal.getActiva());
    }
*/




    @Test
    @DisplayName("Debería obtener reservas por cliente")
    public void testObtenerReservasPorCliente() {
        // Dado que
        Long clienteId = usuarioGlobal.getId();
        given(reservaRepository.findByEmpleadoId(clienteId)).willReturn(List.of(reservaGlobal));

        // Cuando
        List<Reserva> reservasCliente = reservaService.obtenerReservasPorUsuario(clienteId);

        // Entonces
        assertThat(reservasCliente).isNotEmpty();
        assertThat(reservasCliente.get(0).getCliente().getId()).isEqualTo(clienteId);
        verify(reservaRepository, times(1)).findByEmpleadoId(clienteId);
    }

    @Test
    @DisplayName("Debería obtener reservas por empleado")
    public void testObtenerReservasPorEmpleado() {
        // Dado que
        Long empleadoId = empleadoGlobal.getId();
        given(reservaRepository.findByEmpleadoId(empleadoId)).willReturn(List.of(reservaGlobal));

        // Cuando
        List<Reserva> reservasEmpleado = reservaService.obtenerReservasPorEmpleado(empleadoId);

        // Entonces
        assertThat(reservasEmpleado).isNotEmpty();
        assertThat(reservasEmpleado.get(0).getEmpleado().getId()).isEqualTo(empleadoId);
        verify(reservaRepository, times(1)).findByEmpleadoId(empleadoId);
    }

    @Test
    @DisplayName("Debería lanzar excepción si la reserva no existe al cancelar")
    public void testCancelarReservaNoExistente() {
        // Dado que
        Long reservaIdInexistente = 99L;
        given(reservaRepository.findById(reservaIdInexistente)).willReturn(Optional.empty());

        // Cuando
        Reserva resultado = reservaService.cancelarReserva(reservaIdInexistente);

        // Entonces
        assertThat(resultado).isNull();
        verify(reservaRepository, times(0)).save(any(Reserva.class));
    }


}
