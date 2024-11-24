package com.sistema.examenes.sistema_examenes_backend.service;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sistema.examenes.sistema_examenes_backend.Enums.DiaSemana;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoComprobante;
import com.sistema.examenes.sistema_examenes_backend.Enums.TipoRecurso;
import com.sistema.examenes.sistema_examenes_backend.configuraciones.DiaSemanaConverter;
import com.sistema.examenes.sistema_examenes_backend.entidades.*;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityNotFoundException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.*;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.CitaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CitaServiceTest {

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
    private CitaServiceImpl citaService;

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


    @DisplayName("Test para crear una cita con recurso de tipo PERSONAL")
    @Test
    public void testCrearCitaConEmpleado(){
        // given
        servicioGlobal.setTipo(TipoRecurso.PERSONAL);
        recursoGlobal.setTipo(TipoRecurso.PERSONAL);

        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));

        citaGlobal.setHoraFin(citaGlobal.getHoraInicio().plusMinutes(servicioGlobal.getDuracionServicio()));

        DiaSemana diaSemana = DiaSemanaConverter.convertirADiaSemana(citaGlobal.getFecha().getDayOfWeek());
        given(horarioLaboralRepository.findByDia(diaSemana)).willReturn(List.of(horarioLunes));

        citaGlobal.setEstado(EstadoCita.AGENDADA);


        citaGlobal.setEmpleado(empleadoGlobal);
        given(empleadoRepository.findById(empleadoGlobal.getId())).willReturn(Optional.of(empleadoGlobal));

        given(citaRepository.save(citaGlobal)).willReturn(citaGlobal);
        given(reservaRepository.save(any(Reserva.class))).willAnswer(invocation -> invocation.getArgument(0));


        // when
        Cita citaGuardada = citaService.crearCita(citaGlobal);


        // then
        assertThat(citaGuardada).isNotNull();
        verify(citaRepository, times(1)).save(citaGlobal); // Verificar que se guardó la cita
    }

    @DisplayName("Test para crear una cita aleatoria con recurso de tipo PERSONAL")
    @Test
    public void testCrearCitaAleatoria() {
        // given
        servicioGlobal.setTipo(TipoRecurso.PERSONAL);
        recursoGlobal.setTipo(TipoRecurso.PERSONAL);

        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));

        citaGlobal.setHoraFin(citaGlobal.getHoraInicio().plusMinutes(servicioGlobal.getDuracionServicio()));

        DiaSemana diaSemana = DiaSemanaConverter.convertirADiaSemana(citaGlobal.getFecha().getDayOfWeek());
        given(horarioLaboralRepository.findByDia(diaSemana)).willReturn(List.of(horarioLunes));

        // Simular empleados habilitados
        List<Usuario> empleadosDisponibles = List.of(empleadoGlobal);
        given(empleadoRepository.findByEnabledTrue()).willReturn(empleadosDisponibles);


        citaGlobal.setEstado(EstadoCita.AGENDADA);
        citaGlobal.setEmpleado(empleadoGlobal); // Se asigna automáticamente un empleado disponible

        given(citaRepository.save(citaGlobal)).willReturn(citaGlobal);
        given(reservaRepository.save(any(Reserva.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Cita citaGuardada = citaService.crearCitaAleatoria(citaGlobal);

        // then
        assertThat(citaGuardada).isNotNull();
        assertThat(citaGuardada.getEmpleado()).isEqualTo(empleadoGlobal); // Verificar que se asignó el empleado
        verify(citaRepository, times(1)).save(citaGlobal); // Verificar que se guardó la cita
    }

    @DisplayName("Test para listar citas")
    @Test
    public void testListarCitas(){
        //given
        //given(citaRepository.save(citaGlobal)).willReturn(citaGlobal);
        given(citaRepository.findAll()).willReturn(List.of(citaGlobal));

        //when
        List<Cita> citas = citaService.obtenerCitas();

        //then
        assertThat(citas).isNotEmpty();
        assertThat(citas.size()).isEqualTo(1);
        verify(citaRepository, times(1)).findAll();
    }

    @DisplayName("Test para obtener cita por Id")
    @Test
    public void testObtenerCitaPorId(){
        //given
        given(citaRepository.findById(1L)).willReturn(Optional.of(citaGlobal));

        //when
        Cita citaObtenida = citaService.obtenerCitaPorId(1L);

        //then
        assertThat(citaObtenida).isNotNull();
        verify(citaRepository, times(1)).findById(1L);

    }

    @DisplayName("Test para obtener citas por usuario")
    @Test
    public void testObtenerCitaPorUsuario() {
        //given
        Long usuarioId = 1L;
        // Simulamos que el repositorio devuelve una lista con la citaGlobal para el usuario con id = 1L
        given(citaRepository.findByClienteId(usuarioId)).willReturn(List.of(citaGlobal));

        //when
        List<Cita> citas = citaService.obtenerCitaPorUsuario(usuarioId);

        //then
        assertThat(citas).isNotEmpty();
        assertThat(citas.size()).isEqualTo(1); // Verificamos que solo haya una cita (la citaGlobal)
        assertThat(citas.get(0).getCliente().getId()).isEqualTo(usuarioId); // Verificamos que la cita esté asociada al usuario correcto
        verify(citaRepository, times(1)).findByClienteId(usuarioId); // Verificamos que el método del repositorio haya sido llamado una vez
    }

    //completar cita

    //obtener cita por empleado
    @DisplayName("Test para obtener citas por empleado")
    @Test
    public void testObtenerCitaPorEmpleado() {
        //given
        List<Cita> citas = Arrays.asList(citaGlobal);
        given(citaRepository.findByEmpleadoId(empleadoGlobal.getId())).willReturn(citas);

        // when
        List<Cita> result = citaService.obtenerCitaPorEmpleado(empleadoGlobal.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1); // Verificamos que se devuelva 1 cita
        assertThat(result.get(0).getEmpleado()).isEqualTo(empleadoGlobal); // Verificamos que el empleado sea el correcto
        verify(citaRepository, times(1)).findByEmpleadoId(empleadoGlobal.getId()); // Verificamos que el repositorio haya sido llamado correctamente
    }

    //obtener citas agendadas
    @DisplayName("Test para obtener citas agendadas")
    @Test
    public void testObtenerCitasAgendadas() {
        //given
        Cita cita1 = new Cita();
        cita1.setIdCita(1L);
        cita1.setEstado(EstadoCita.AGENDADA);

        Cita cita2 = new Cita();
        cita2.setIdCita(2L);
        cita2.setEstado(EstadoCita.AGENDADA);

        List<Cita> citasAgendadas = Arrays.asList(cita1, cita2);

        // Simulamos que el repositorio devuelve las citas con estado AGENDADA
        given(citaRepository.findByEstado(EstadoCita.AGENDADA)).willReturn(citasAgendadas);

        //when
        List<Cita> result = citaService.obtenerCitasAgendadas();

        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2); // Verificamos que se devuelvan 2 citas
        assertThat(result.get(0).getEstado()).isEqualTo(EstadoCita.AGENDADA); // Verificamos que el estado sea AGENDADA
        verify(citaRepository, times(1)).findByEstado(EstadoCita.AGENDADA); // Verificamos que el repositorio haya sido llamado
    }

    @DisplayName("Test para obtener citas canceladas")
    @Test
    public void testObtenerCitasCanceladas() {
        //given
        Cita cita1 = new Cita();
        cita1.setIdCita(1L);
        cita1.setEstado(EstadoCita.CANCELADA);

        Cita cita2 = new Cita();
        cita2.setIdCita(2L);
        cita2.setEstado(EstadoCita.CANCELADA);

        List<Cita> citasCanceladas = Arrays.asList(cita1, cita2);

        // Simulamos que el repositorio devuelve las citas con estado CANCELADA
        given(citaRepository.findByEstado(EstadoCita.CANCELADA)).willReturn(citasCanceladas);

        //when
        List<Cita> result = citaService.obtenerCitasCanceladas();

        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2); // Verificamos que se devuelvan 2 citas
        assertThat(result.get(0).getEstado()).isEqualTo(EstadoCita.CANCELADA); // Verificamos que el estado sea CANCELADA
        verify(citaRepository, times(1)).findByEstado(EstadoCita.CANCELADA); // Verificamos que el repositorio haya sido llamado
    }


    @DisplayName("Test para obtener citas realizadas")
    @Test
    public void testObtenerCitasRealizadas() {
        //given
        Cita cita1 = new Cita();
        cita1.setIdCita(1L);
        cita1.setEstado(EstadoCita.REALIZADA);

        Cita cita2 = new Cita();
        cita2.setIdCita(2L);
        cita2.setEstado(EstadoCita.REALIZADA);

        List<Cita> citasRealizadas = Arrays.asList(cita1, cita2);

        // Simulamos que el repositorio devuelve las citas con estado REALIZADA
        given(citaRepository.findByEstado(EstadoCita.REALIZADA)).willReturn(citasRealizadas);

        //when
        List<Cita> result = citaService.obtenerCitasRealizadas();

        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2); // Verificamos que se devuelvan 2 citas
        assertThat(result.get(0).getEstado()).isEqualTo(EstadoCita.REALIZADA); // Verificamos que el estado sea REALIZADA
        verify(citaRepository, times(1)).findByEstado(EstadoCita.REALIZADA); // Verificamos que el repositorio haya sido llamado
    }


    @DisplayName("Test para crear una cita con fecha anterior a la actual")
    @Test
    public void testCrearCitaFechaAnterior() {
        // given
        citaGlobal.setFecha(LocalDate.now().minusDays(1)); // Fecha en el pasado

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            citaService.crearCita(citaGlobal);
        });

        assertThat(exception.getMessage()).isEqualTo("La cita debe ser programada para una fecha futura.");

        verify(citaRepository, times(0)).save(any(Cita.class));
    }

    @DisplayName("Test para crear una cita aleatoria con fecha anterior a la actual")
    @Test
    public void testCrearCitaAleatoriaFechaAnterior() {
        // given
        citaGlobal.setFecha(LocalDate.now().minusDays(1)); // Fecha en el pasado

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            citaService.crearCitaAleatoria(citaGlobal);
        });

        assertThat(exception.getMessage()).isEqualTo("La cita debe ser programada para una fecha futura.");

        verify(citaRepository, times(0)).save(any(Cita.class));
    }

    @DisplayName("Test para crear una cita con un servicio no disponible o no encontrado")
    @Test
    public void testCrearCitaServicioNoDisponible() {
        // given
        Long servicioIdInvalido = 999L; // ID que no existe
        citaGlobal.setServicio(new Servicio()); // Asignar un servicio ficticio a la cita
        citaGlobal.getServicio().setServicioId(servicioIdInvalido);

        // Configurar el mock para que devuelva vacío al buscar el servicio
        given(servicioRepository.findById(servicioIdInvalido)).willReturn(Optional.empty());

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            citaService.crearCita(citaGlobal);
        });

        // Validar el mensaje de la excepción
        assertThat(exception.getMessage()).isEqualTo("Servicio no encontrado");

        // Verificar que no se guardó la cita
        verify(citaRepository, times(0)).save(any(Cita.class));
    }

    @DisplayName("Test para crear una cita aleatoria con un servicio no disponible o no encontrado")
    @Test
    public void testCrearCitaAleatoriaServicioNoDisponible() {
        // given
        Long servicioIdInvalido = 999L; // ID que no existe
        citaGlobal.setServicio(new Servicio()); // Asignar un servicio ficticio a la cita
        citaGlobal.getServicio().setServicioId(servicioIdInvalido);

        // Configurar el mock para que devuelva vacío al buscar el servicio
        given(servicioRepository.findById(servicioIdInvalido)).willReturn(Optional.empty());

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            citaService.crearCitaAleatoria(citaGlobal);
        });

        // Validar el mensaje de la excepción
        assertThat(exception.getMessage()).isEqualTo("Servicio no encontrado");

        // Verificar que no se guardó la cita
        verify(citaRepository, times(0)).save(any(Cita.class));
    }

    @DisplayName("Test para crear una cita con un recurso no disponible o no encontrado")
    @Test
    public void testCrearCitaRecursoNoDisponible() {
        // given
        recursoGlobal.setTipo(TipoRecurso.PERSONAL);
        Long servicioIdInvalido = 999L; // ID que no existe
        citaGlobal.setServicio(new Servicio()); // Asignar un servicio ficticio a la cita
        citaGlobal.getServicio().setServicioId(servicioIdInvalido);

        // Configurar el mock para que devuelva vacío al buscar el servicio
        given(servicioRepository.findById(servicioIdInvalido)).willReturn(Optional.empty());

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            citaService.crearCita(citaGlobal);
        });

        // Validar el mensaje de la excepción
        assertThat(exception.getMessage()).isEqualTo("Servicio no encontrado");

        // Verificar que no se guardó la cita
        verify(citaRepository, times(0)).save(any(Cita.class));
    }

    @DisplayName("Test para crear una cita aleatoria con un recurso no disponible o no encontrado")
    @Test
    public void testCrearCitaAleatoriaRecursoNoDisponible() {
        // given
        recursoGlobal.setTipo(TipoRecurso.PERSONAL);
        Long servicioIdInvalido = 999L; // ID que no existe
        citaGlobal.setServicio(new Servicio()); // Asignar un servicio ficticio a la cita
        citaGlobal.getServicio().setServicioId(servicioIdInvalido);

        // Configurar el mock para que devuelva vacío al buscar el servicio
        given(servicioRepository.findById(servicioIdInvalido)).willReturn(Optional.empty());

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            citaService.crearCitaAleatoria(citaGlobal);
        });

        // Validar el mensaje de la excepción
        assertThat(exception.getMessage()).isEqualTo("Servicio no encontrado");

        // Verificar que no se guardó la cita
        verify(citaRepository, times(0)).save(any(Cita.class));
    }

    @DisplayName("Test para crear una cita con un recurso con tipo incorrecto")
    @Test
    public void testCrearCitaRecursoTipoIncorrecto() {
        // given
        servicioGlobal.setTipo(TipoRecurso.PERSONAL); // El servicio es de tipo PERSONAL
        recursoGlobal.setTipo(TipoRecurso.INSTALACION); // El recurso es de tipo INSTALACION

        // Configurar la cita con los objetos globales
        citaGlobal.setServicio(servicioGlobal);
        citaGlobal.setEmpleado(empleadoGlobal); // El empleado global es asignado a la cita
        citaGlobal.setRecurso(recursoGlobal);

        // Configurar los mocks para que devuelvan los objetos globales
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        //given(empleadoRepository.findById(empleadoGlobal.getId())).willReturn(Optional.of(empleadoGlobal));
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            citaService.crearCita(citaGlobal); // Intentamos crear la cita
        });

        // Validar que el mensaje de la excepción sea el esperado, en este caso por tipo incorrecto de recurso
        assertThat(exception.getMessage()).isEqualTo("El recurso debe ser del mismo tipo que el servicio.");

        // Verificar que no se guardó la cita
        verify(citaRepository, times(0)).save(any(Cita.class));
    }

    @DisplayName("Test para crear una cita aleatoria con un recurso con tipo incorrecto")
    @Test
    public void testCrearCitaAleatoriaRecursoTipoIncorrecto() {
        // given
        servicioGlobal.setTipo(TipoRecurso.PERSONAL); // El servicio es de tipo PERSONAL
        recursoGlobal.setTipo(TipoRecurso.INSTALACION); // El recurso es de tipo INSTALACION

        // Configurar la cita con los objetos globales
        citaGlobal.setServicio(servicioGlobal);
        citaGlobal.setEmpleado(empleadoGlobal); // El empleado global es asignado a la cita
        citaGlobal.setRecurso(recursoGlobal);

        // Configurar los mocks para que devuelvan los objetos globales
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            citaService.crearCitaAleatoria(citaGlobal); // Intentamos crear la cita
        });

        assertThat(exception.getMessage()).isEqualTo("El recurso debe ser del mismo tipo que el servicio.");
        verify(citaRepository, times(0)).save(any(Cita.class));
    }


    @DisplayName("Test para crear una cita con servicio de tipo PERSONAL sin empleado asignado")
    @Test
    public void testCrearCitaServicioPersonalSinEmpleado() {
        // given
        recursoGlobal.setTipo(TipoRecurso.PERSONAL);
        servicioGlobal.setTipo(TipoRecurso.PERSONAL); // Asegurar que el servicio sea del mismo tipo
        citaGlobal.setEmpleado(null); // No se asigna empleado

        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));
        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            citaService.crearCita(citaGlobal);
        });

        // Validar el mensaje de la excepción
        assertThat(exception.getMessage()).isEqualTo("El empleado es obligatorio para crear una cita con un servicio personal.");

        // Verificar que no se guardó la cita
        verify(citaRepository, times(0)).save(any(Cita.class));
    }


    @DisplayName("Test para crear una cita con recurso PERSONAL y empleado que no existe")
    @Test
    public void testCrearCitaEmpleadoNoExiste() {
        // given
        servicioGlobal.setTipo(TipoRecurso.PERSONAL);
        recursoGlobal.setTipo(TipoRecurso.PERSONAL);
        citaGlobal.setEmpleado(empleadoGlobal); // Asignamos un empleado
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));


        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            citaService.crearCita(citaGlobal);
        });

        // Validar el mensaje de la excepción
        assertThat(exception.getMessage()).isEqualTo("El empleado no tiene permisos para realizar este servicio.");

        // Verificar que no se guardó la cita
        verify(citaRepository, times(0)).save(any(Cita.class));
    }

    @DisplayName("Test para crear una cita con recurso PERSONAL y empleado deshabilitado (activo = false)")
    @Test
    public void testCrearCitaEmpleadoDeshabilitado() {
        // given
        recursoGlobal.setTipo(TipoRecurso.PERSONAL);
        servicioGlobal.setTipo(TipoRecurso.PERSONAL);
        citaGlobal.setEmpleado(empleadoGlobal); // Asignamos un empleado
        empleadoGlobal.setEnabled(false); // Empleado deshabilitado
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));


        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            citaService.crearCita(citaGlobal);
        });

        // Validar el mensaje de la excepción
        assertThat(exception.getMessage()).isEqualTo("El empleado no tiene permisos para realizar este servicio.");

        // Verificar que no se guardó la cita
        verify(citaRepository, times(0)).save(any(Cita.class));
    }

    @DisplayName("Test para crear una cita con recurso PERSONAL y empleado sin rol EMPLEADO")
    @Test
    public void testCrearCitaEmpleadoSinRol() {
        // given
        servicioGlobal.setTipo(TipoRecurso.PERSONAL);
        recursoGlobal.setTipo(TipoRecurso.PERSONAL);
        citaGlobal.setEmpleado(empleadoGlobal); // Asignamos un empleado

        // Empleado sin rol EMPLEADO
        Rol rolNoEmpleado = new Rol();
        rolNoEmpleado.setRolNombre("CLIENTE"); // Rol incorrecto
        UsuarioRol usuarioRolNoEmpleado = new UsuarioRol();
        usuarioRolNoEmpleado.setRol(rolNoEmpleado);
        usuarioRolNoEmpleado.setUsuario(empleadoGlobal);

        Set<UsuarioRol> rolesEmpleado = new HashSet<>();
        rolesEmpleado.add(usuarioRolNoEmpleado);
        empleadoGlobal.setUsuarioRoles(rolesEmpleado); // Establecemos el rol incorrecto

        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            citaService.crearCita(citaGlobal);
        });

        // Validar el mensaje de la excepción
        assertThat(exception.getMessage()).isEqualTo("El empleado no tiene permisos para realizar este servicio.");

        // Verificar que no se guardó la cita
        verify(citaRepository, times(0)).save(any(Cita.class));
    }

    @DisplayName("Test para crear una cita sin empleado")
    @Test
    public void testCrearCitaSinEmpleado(){
        //given
        servicioGlobal.setTipo(TipoRecurso.INSTALACION);
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        citaGlobal.setHoraFin(citaGlobal.getHoraInicio().plusMinutes(servicioGlobal.getDuracionServicio()));
        DiaSemana diaSemana = DiaSemanaConverter.convertirADiaSemana(citaGlobal.getFecha().getDayOfWeek());
        given(horarioLaboralRepository.findByDia(diaSemana)).willReturn(List.of(horarioLunes));
        citaGlobal.setEstado(EstadoCita.AGENDADA);
        citaGlobal.setEmpleado(null);
        given(citaRepository.save(citaGlobal)).willReturn(citaGlobal);
        //given(reservaRepository.save(reservaGlobal)).willReturn(reservaGlobal);

        given(reservaRepository.findByRecursoAndFecha(recursoGlobal, citaGlobal.getFecha()))
                .willReturn(List.of(reservaGlobal));
        given(reservaRepository.save(any(Reserva.class))).willAnswer(invocation -> invocation.getArgument(0));


        //when
        Cita citaGuardada = citaService.crearCita(citaGlobal);

        //then
        assertThat(citaGuardada).isNotNull();
        verify(citaRepository, times(1)).save(citaGlobal);
    }

    @DisplayName("Test para verificar excepción al intentar crear cita en día festivo")
    @Test
    public void testCrearCitaEnDiaFestivo() {
        // given
        servicioGlobal.setTipo(TipoRecurso.INSTALACION);
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        citaGlobal.setHoraFin(citaGlobal.getHoraInicio().plusMinutes(servicioGlobal.getDuracionServicio()));
        DiaSemana diaSemana = DiaSemanaConverter.convertirADiaSemana(citaGlobal.getFecha().getDayOfWeek());
        citaGlobal.setEstado(EstadoCita.AGENDADA);
        citaGlobal.setEmpleado(null);
        LocalDate fechaFestiva = LocalDate.of(2024, 12, 25); // Ejemplo: Navidad
        citaGlobal.setFecha(fechaFestiva);

        DiaFestivo navidad = new DiaFestivo();
        navidad.setFecha(fechaFestiva);
        given(diaFestivoRepository.findAll()).willReturn(List.of(navidad)); // Mock de días festivos

        // when/then
        assertThatThrownBy(() -> citaService.crearCita(citaGlobal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No se pueden crear citas en días festivos.");
    }

    @DisplayName("Test para cita en día sin horarios laborales")
    @Test
    public void testCitaEnDiaSinHorariosLaborales() {
        // given
        servicioGlobal.setTipo(TipoRecurso.INSTALACION);
        LocalDate fecha = LocalDate.of(2024, 11, 22); // Fecha cualquiera
        citaGlobal.setFecha(fecha);
        citaGlobal.setHoraInicio(LocalTime.of(10, 0)); // Hora de inicio de la cita
        citaGlobal.setHoraFin(LocalTime.of(11, 0));    // Hora de fin de la cita

        DiaSemana diaSemana = DiaSemanaConverter.convertirADiaSemana(fecha.getDayOfWeek());


        // when/then
        assertThatThrownBy(() -> citaService.crearCita(citaGlobal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La cita debe ser programada para una fecha futura.");
    }

    @DisplayName("Test para cita dentro del horario laboral")
    @Test
    public void testCitaDentroHorarioLaboral() {
        // given
        servicioGlobal.setTipo(TipoRecurso.INSTALACION);
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        LocalDate fecha = LocalDate.of(2024, 12, 10);
        citaGlobal.setFecha(fecha);
        citaGlobal.setHoraInicio(LocalTime.of(10, 0));
        citaGlobal.setHoraFin(LocalTime.of(11, 0));

        DiaSemana diaSemana = DiaSemanaConverter.convertirADiaSemana(fecha.getDayOfWeek());
        HorarioLaboral horarioLaboral = new HorarioLaboral();
        horarioLaboral.setDia(diaSemana);
        horarioLaboral.setHoraInicio(LocalTime.of(9, 0));
        horarioLaboral.setHoraFin(LocalTime.of(17, 0));
        given(horarioLaboralRepository.findByDia(diaSemana)).willReturn(List.of(horarioLaboral));

        given(citaRepository.save(citaGlobal)).willReturn(citaGlobal); // Mock para guardar la cita
        given(reservaRepository.save(any(Reserva.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Cita citaGuardada = citaService.crearCita(citaGlobal);

        // then
        assertThat(citaGuardada).isNotNull();
        verify(citaRepository, times(1)).save(citaGlobal); // Verificar que se guardó
    }


    @DisplayName("Test para cita fuera del rango de horario laboral")
    @Test
    public void testCitaFueraDeHorarioLaboral() {
        // given
        servicioGlobal.setTipo(TipoRecurso.INSTALACION);
        //given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));
        //given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        LocalDate fecha = LocalDate.of(2024, 11, 22);
        citaGlobal.setFecha(fecha);
        citaGlobal.setHoraInicio(LocalTime.of(18, 0)); // Hora después del horario laboral
        citaGlobal.setHoraFin(LocalTime.of(19, 0));    // Hora después del horario laboral

        DiaSemana diaSemana = DiaSemanaConverter.convertirADiaSemana(fecha.getDayOfWeek());
        HorarioLaboral horarioLaboral = new HorarioLaboral();
        horarioLaboral.setDia(diaSemana);
        horarioLaboral.setHoraInicio(LocalTime.of(9, 0));
        horarioLaboral.setHoraFin(LocalTime.of(17, 0));
        //given(horarioLaboralRepository.findByDia(diaSemana)).willReturn(List.of(horarioLaboral));

        // when/then
        assertThatThrownBy(() -> citaService.crearCita(citaGlobal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La cita debe ser programada para una fecha futura.");
    }

    @DisplayName("Test para recurso no disponible o inexistente")
    @Test
    public void testRecursoDisponible() {
        // given
        servicioGlobal.setTipo(TipoRecurso.INSTALACION);
        recursoGlobal.setTipo(TipoRecurso.INSTALACION);
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.empty());

        // when/then
        assertThatThrownBy(() -> citaService.crearCita(citaGlobal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Recurso no encontrado");
    }

    @DisplayName("Test para servicio no disponible o inexistente")
    @Test
    public void testServicioDisponible() {

        recursoGlobal.setTipo(TipoRecurso.INSTALACION);

        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.empty()); // Servicio no encontrado

        // when: Intentamos crear una cita con un servicio inexistente
        // then: Se lanza una excepción con el mensaje esperado
        assertThatThrownBy(() -> citaService.crearCita(citaGlobal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Servicio no encontrado");
    }

    @DisplayName("Test para crear una cita con recurso de tipo PERSONAL")
    @Test
    public void testServicioNoDisponible(){
        // given
        servicioGlobal.setTipo(TipoRecurso.PERSONAL);
        servicioGlobal.setDisponible(false);
        recursoGlobal.setTipo(TipoRecurso.PERSONAL);

        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));

        citaGlobal.setHoraFin(citaGlobal.getHoraInicio().plusMinutes(servicioGlobal.getDuracionServicio()));

        DiaSemana diaSemana = DiaSemanaConverter.convertirADiaSemana(citaGlobal.getFecha().getDayOfWeek());
        given(horarioLaboralRepository.findByDia(diaSemana)).willReturn(List.of(horarioLunes));

        citaGlobal.setEstado(EstadoCita.AGENDADA);


        citaGlobal.setEmpleado(empleadoGlobal);
        given(empleadoRepository.findById(empleadoGlobal.getId())).willReturn(Optional.of(empleadoGlobal));

        // when
        //Cita citaGuardada = citaService.crearCita(citaGlobal);

        // then
        assertThatThrownBy(() -> citaService.crearCita(citaGlobal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El servicio no está disponible o no existe.");
    }

    @DisplayName("Test para crear una cita con recurso de tipo PERSONAL")
    @Test
    public void testRecursoNoDisponible(){
        // given
        servicioGlobal.setTipo(TipoRecurso.PERSONAL);
        recursoGlobal.setDisponible(false);
        recursoGlobal.setTipo(TipoRecurso.PERSONAL);

        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));

        citaGlobal.setHoraFin(citaGlobal.getHoraInicio().plusMinutes(servicioGlobal.getDuracionServicio()));

        DiaSemana diaSemana = DiaSemanaConverter.convertirADiaSemana(citaGlobal.getFecha().getDayOfWeek());
        given(horarioLaboralRepository.findByDia(diaSemana)).willReturn(List.of(horarioLunes));

        citaGlobal.setEstado(EstadoCita.AGENDADA);


        citaGlobal.setEmpleado(empleadoGlobal);
        given(empleadoRepository.findById(empleadoGlobal.getId())).willReturn(Optional.of(empleadoGlobal));

        // when
        //Cita citaGuardada = citaService.crearCita(citaGlobal);

        // then
        assertThatThrownBy(() -> citaService.crearCita(citaGlobal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El recurso no está disponible o no existe.");
    }

    @DisplayName("Test para manejar excepción ServicioNoEncontradoException")
    @Test
    public void testCrearCitaAleatoriaServicioNoEncontrado() {
        // given
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> citaService.crearCitaAleatoria(citaGlobal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Servicio no encontrado");
    }

    @DisplayName("Test para manejar excepción RecursoNoEncontradoException")
    @Test
    public void testCrearCitaAleatoriaRecursoNoEncontrado() {
        // given
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> citaService.crearCitaAleatoria(citaGlobal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Recurso no encontrado");
    }


    @DisplayName("Test para obtener cita por Id no existente")
    @Test
    public void testObtenerCitaPorIdNoExistente() {
        // given
        given(citaRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> citaService.obtenerCitaPorId(1L));
        verify(citaRepository, times(1)).findById(1L);
    }

    @DisplayName("Test para cancelar cita existente")
    @Test
    public void testCancelarCitaCambiaEstadoACancelada() {
        // given: la cita existe en el repositorio
        given(citaRepository.findById(1L)).willReturn(Optional.of(citaGlobal));
        given(citaRepository.save(any(Cita.class))).willAnswer(invocation -> invocation.getArgument(0)); // Simula guardar la cita

        // when: se llama al método cancelarCita
        Cita citaCancelada = citaService.cancelarCita(1L);

        // then: el estado debe ser CANCELADA y se debe guardar la cita actualizada
        assertThat(citaCancelada).isNotNull();
        assertThat(citaCancelada.getEstado()).isEqualTo(EstadoCita.CANCELADA);

        verify(citaRepository, times(1)).findById(1L);
        verify(citaRepository, times(1)).save(citaCancelada);
    }

    @DisplayName("Test para desactivar la reserva asociada al cancelar una cita")
    @Test
    public void testDesactivarReservaAsociada() {
        // given: la cita y la reserva existen en los repositorios
        given(citaRepository.findById(1L)).willReturn(Optional.of(citaGlobal));
        given(reservaRepository.findByCitaIdCita(1L)).willReturn(reservaGlobal);
        given(reservaRepository.save(any(Reserva.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when: se cancela la cita
        citaService.cancelarCita(1L);

        // then: verificar que la reserva fue desactivada
        assertThat(reservaGlobal.getActiva()).isFalse(); // El estado activa debe ser false
        verify(reservaRepository, times(1)).findByCitaIdCita(1L);
        verify(reservaRepository, times(1)).save(reservaGlobal); // La reserva debe guardarse actualizada
    }

    @DisplayName("Test para confirmar cita existente")
    @Test
    public void testCancelarCitaCambiaEstadoAConfirmada() {
        // given: la cita existe en el repositorio
        given(citaRepository.findById(1L)).willReturn(Optional.of(citaGlobal));
        given(citaRepository.save(any(Cita.class))).willAnswer(invocation -> invocation.getArgument(0)); // Simula guardar la cita

        // when: se llama al método cancelarCita
        Cita citaConfirmada = citaService.confirmarCita(1L);

        // then: el estado debe ser CANCELADA y se debe guardar la cita actualizada
        assertThat(citaConfirmada).isNotNull();
        assertThat(citaConfirmada.getEstado()).isEqualTo(EstadoCita.CONFIRMADA);

        verify(citaRepository, times(1)).findById(1L);
        verify(citaRepository, times(1)).save(citaConfirmada);
    }


    @DisplayName("Test para completa cita existente")
    @Test
    public void testCancelarCitaCambiaEstadoACompletada() {
        // given: la cita existe en el repositorio
        given(citaRepository.findById(1L)).willReturn(Optional.of(citaGlobal));
        given(citaRepository.save(any(Cita.class))).willAnswer(invocation -> invocation.getArgument(0)); // Simula guardar la cita


        Cita citaCompletada = citaService.completarCita(1L);

        // then: el estado debe ser CANCELADA y se debe guardar la cita actualizada
        assertThat(citaCompletada).isNotNull();
        assertThat(citaCompletada.getEstado()).isEqualTo(EstadoCita.REALIZADA);

        verify(citaRepository, times(1)).findById(1L);
        verify(citaRepository, times(1)).save(citaCompletada);
    }


    @DisplayName("Test para lanzar excepción cuando no hay empleados disponibles con rol 'EMPLEADO'")
    @Test
    public void testCrearCitaAleatoriaSinEmpleadosDisponibles() {
        // given
        servicioGlobal.setTipo(TipoRecurso.PERSONAL);
        recursoGlobal.setTipo(TipoRecurso.PERSONAL);

        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));

        given(empleadoRepository.findByEnabledTrue()).willReturn(List.of()); // No hay empleados habilitados

        // when - then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> citaService.crearCitaAleatoria(citaGlobal));

        // Verificar el mensaje de la excepción
        assertThat(exception.getMessage()).isEqualTo("No hay empleados con el rol 'EMPLEADO' disponibles para la cita.");
        verify(empleadoRepository, times(1)).findByEnabledTrue(); // Se consultó la lista de empleados
    }


    @DisplayName("Test para lanzar excepción cuando no hay empleados disponibles en el horario solicitado")
    @Test
    public void testCrearCitaAleatoriaSinEmpleadosDisponiblesEnHorario() {
        // given
        servicioGlobal.setTipo(TipoRecurso.PERSONAL);
        recursoGlobal.setTipo(TipoRecurso.PERSONAL);

        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));

        // Simular que no hay empleados habilitados
        List<Usuario> empleadosDisponibles = new ArrayList<>(); // Lista vacía para simular que no hay empleados disponibles
        given(empleadoRepository.findByEnabledTrue()).willReturn(empleadosDisponibles);

        // when - then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> citaService.crearCitaAleatoria(citaGlobal));

        // Verificar el mensaje de la excepción
        assertThat(exception.getMessage()).isEqualTo("No hay empleados con el rol 'EMPLEADO' disponibles para la cita.");

        // Verificar que se consultó la lista de empleados habilitados
        verify(empleadoRepository, times(1)).findByEnabledTrue();
    }

    @DisplayName("Test para verificar excepción al intentar crear cita  aleatoria en día festivo")
    @Test
    public void testCrearCitaAleatoriaEnDiaFestivo() {
        // given
        servicioGlobal.setTipo(TipoRecurso.INSTALACION);
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        citaGlobal.setHoraFin(citaGlobal.getHoraInicio().plusMinutes(servicioGlobal.getDuracionServicio()));
        DiaSemana diaSemana = DiaSemanaConverter.convertirADiaSemana(citaGlobal.getFecha().getDayOfWeek());
        citaGlobal.setEstado(EstadoCita.AGENDADA);
        citaGlobal.setEmpleado(null);
        LocalDate fechaFestiva = LocalDate.of(2024, 12, 25); // Ejemplo: Navidad
        citaGlobal.setFecha(fechaFestiva);

        DiaFestivo navidad = new DiaFestivo();
        navidad.setFecha(fechaFestiva);
        given(diaFestivoRepository.findAll()).willReturn(List.of(navidad)); // Mock de días festivos

        // when/then
        assertThatThrownBy(() -> citaService.crearCitaAleatoria(citaGlobal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No se pueden crear citas en días festivos.");
    }

    @DisplayName("Test para crear una cita con recurso de tipo PERSONAL")
    @Test
    public void testServicioNoDisponibleAleatoria(){
        // given
        servicioGlobal.setTipo(TipoRecurso.PERSONAL);
        servicioGlobal.setDisponible(false);
        recursoGlobal.setTipo(TipoRecurso.PERSONAL);

        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));

        citaGlobal.setHoraFin(citaGlobal.getHoraInicio().plusMinutes(servicioGlobal.getDuracionServicio()));

        DiaSemana diaSemana = DiaSemanaConverter.convertirADiaSemana(citaGlobal.getFecha().getDayOfWeek());
        given(horarioLaboralRepository.findByDia(diaSemana)).willReturn(List.of(horarioLunes));

        List<Usuario> empleadosDisponibles = List.of(empleadoGlobal);
        //given(empleadoRepository.findByEnabledTrue()).willReturn(empleadosDisponibles);

        citaGlobal.setEstado(EstadoCita.AGENDADA);


        citaGlobal.setEmpleado(empleadoGlobal);
        given(empleadoRepository.findById(empleadoGlobal.getId())).willReturn(Optional.of(empleadoGlobal));

        // when
        //Cita citaGuardada = citaService.crearCita(citaGlobal);

        // then
        assertThatThrownBy(() -> citaService.crearCita(citaGlobal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El servicio no está disponible o no existe.");
    }

    @DisplayName("Test para crear una cita con recurso de tipo PERSONAL no disponible")
    @Test
    public void testRecursoNoDisponibleAleatoria(){
        // given
        servicioGlobal.setTipo(TipoRecurso.PERSONAL);

        recursoGlobal.setTipo(TipoRecurso.PERSONAL);
        recursoGlobal.setDisponible(false);

        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));

        citaGlobal.setHoraFin(citaGlobal.getHoraInicio().plusMinutes(servicioGlobal.getDuracionServicio()));

        DiaSemana diaSemana = DiaSemanaConverter.convertirADiaSemana(citaGlobal.getFecha().getDayOfWeek());
        given(horarioLaboralRepository.findByDia(diaSemana)).willReturn(List.of(horarioLunes));

        List<Usuario> empleadosDisponibles = List.of(empleadoGlobal);
        //given(empleadoRepository.findByEnabledTrue()).willReturn(empleadosDisponibles);

        citaGlobal.setEstado(EstadoCita.AGENDADA);


        citaGlobal.setEmpleado(empleadoGlobal);
        given(empleadoRepository.findById(empleadoGlobal.getId())).willReturn(Optional.of(empleadoGlobal));

        // when
        //Cita citaGuardada = citaService.crearCita(citaGlobal);

        // then
        assertThatThrownBy(() -> citaService.crearCita(citaGlobal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El recurso no está disponible o no existe.");
    }

    @DisplayName("Test para actualizaCita que no hace nada y retorna null")
    @Test
    public void testActualizaCita() {
        // given
        Cita cita = new Cita(); // Simula una cita

        // when
        Cita resultado = citaService.actualizaCita(cita);

        // then
        assertThat(resultado).isNull(); // Se espera que el resultado sea null
    }



}
