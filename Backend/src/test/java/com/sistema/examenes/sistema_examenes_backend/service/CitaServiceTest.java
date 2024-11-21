package com.sistema.examenes.sistema_examenes_backend.service;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sistema.examenes.sistema_examenes_backend.Enums.DiaSemana;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
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


    }


    @DisplayName("Test para crear una cita con recurso de tipo PERSONAL")
    @Test
    public void testCrearCitaConEmpleado(){
        // given
        recursoGlobal.setTipo(TipoRecurso.PERSONAL); // Asegurarse de que el recurso sea de tipo PERSONAL
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        citaGlobal.setHoraFin(citaGlobal.getHoraInicio().plusMinutes(servicioGlobal.getDuracionServicio()));
        DiaSemana diaSemana = DiaSemanaConverter.convertirADiaSemana(citaGlobal.getFecha().getDayOfWeek());
        given(horarioLaboralRepository.findByDia(diaSemana)).willReturn(List.of(horarioLunes));
        citaGlobal.setEstado(EstadoCita.AGENDADA);


        citaGlobal.setEmpleado(empleadoGlobal);
        given(empleadoRepository.findById(empleadoGlobal.getId())).willReturn(Optional.of(empleadoGlobal));


        given(reservaRepository.findByRecursoAndFecha(recursoGlobal, citaGlobal.getFecha()))
                .willReturn(List.of(reservaGlobal));


        given(citaRepository.save(citaGlobal)).willReturn(citaGlobal);

        // when
        Cita citaGuardada = citaService.crearCita(citaGlobal);

        // then
        assertThat(citaGuardada).isNotNull();
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


    //obtener cita por usuario
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


    //actualizar cita

    // cancelarCita

    //confirmar cita
    @DisplayName("Test para confirmar una cita")
    @Test
    public void testConfirmarCita() {
        //given
        Long citaId = 1L;
        // Creamos una cita con estado PENDIENTE
        Cita cita = new Cita();
        cita.setIdCita(citaId);
        cita.setEstado(EstadoCita.AGENDADA);

        // Simulamos que el repositorio devuelve la cita cuando se busca por ID
        given(citaRepository.findById(citaId)).willReturn(Optional.of(cita));
        // Simulamos que el repositorio guarda la cita después de confirmar
        given(citaRepository.save(any(Cita.class))).willReturn(cita);

        //when
        Cita citaConfirmada = citaService.confirmarCita(citaId);

        //then
        assertThat(citaConfirmada).isNotNull();
        assertThat(citaConfirmada.getEstado()).isEqualTo(EstadoCita.CONFIRMADA); // Verificamos que el estado sea CONFIRMADA
        verify(citaRepository, times(1)).save(cita); // Verificamos que el método save haya sido llamado
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


    //obtener citas canceladas

    //obtener citas realizadas



    @DisplayName("Test para crear una cita con fecha anterior a la actual")
    @Test
    public void testCrearCitaFechaAnterior() {
        // given
        citaGlobal.setFecha(LocalDate.now().minusDays(1)); // Fecha en el pasado

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            citaService.crearCita(citaGlobal);
        });

        // Validar el mensaje de la excepción
        assertThat(exception.getMessage()).isEqualTo("La cita debe ser programada para una fecha futura.");

        // Verificar que no se guardó la cita
        verify(citaRepository, times(0)).save(any(Cita.class));
    }

    @DisplayName("Test para crear una cita con un recurso no disponible o no encontrado")
    @Test
    public void testCrearCitaRecursoNoDisponible() {
        // given
        Long recursoIdInvalido = 999L; // ID que no existe
        citaGlobal.getRecurso().setRecursoId(recursoIdInvalido);

        // Configurar el mock para que devuelva vacío al buscar el recurso
        given(recursoRepository.findById(recursoIdInvalido)).willReturn(Optional.empty());

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            citaService.crearCita(citaGlobal);
        });

        // Validar el mensaje de la excepción
        assertThat(exception.getMessage()).isEqualTo("Recurso no encontrado");

        // Verificar que no se guardó la cita
        verify(citaRepository, times(0)).save(any(Cita.class));
    }

    @DisplayName("Test para crear una cita con recurso de tipo PERSONAL sin empleado asignado")
    @Test
    public void testCrearCitaRecursoPersonalSinEmpleado() {
        // given
        recursoGlobal.setTipo(TipoRecurso.PERSONAL);
        citaGlobal.setEmpleado(null); // No se asigna empleado
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            citaService.crearCita(citaGlobal);
        });

        // Validar el mensaje de la excepción
        assertThat(exception.getMessage()).isEqualTo("El empleado es obligatorio para crear una cita con un recurso personal.");

        // Verificar que no se guardó la cita
        verify(citaRepository, times(0)).save(any(Cita.class));
    }

    @DisplayName("Test para crear una cita con recurso PERSONAL y empleado que no existe")
    @Test
    public void testCrearCitaEmpleadoNoExiste() {
        // given
        recursoGlobal.setTipo(TipoRecurso.PERSONAL);
        citaGlobal.setEmpleado(empleadoGlobal); // Asignamos un empleado
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

    @DisplayName("Test para crear una cita con recurso PERSONAL y empleado deshabilitado (activo = false)")
    @Test
    public void testCrearCitaEmpleadoDeshabilitado() {
        // given
        recursoGlobal.setTipo(TipoRecurso.PERSONAL);
        citaGlobal.setEmpleado(empleadoGlobal); // Asignamos un empleado
        empleadoGlobal.setEnabled(false); // Empleado deshabilitado
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

    @DisplayName("Test para crear una cita")
    @Test
    public void testCrearCitaSinEmpleado(){
        //given
        recursoGlobal.setTipo(TipoRecurso.INSTALACION);
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


        //when
        Cita citaGuardada = citaService.crearCita(citaGlobal);

        //then
        assertThat(citaGuardada).isNotNull();
        verify(citaRepository, times(1)).save(citaGlobal);
    }






}
