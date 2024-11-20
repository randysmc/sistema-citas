package com.sistema.examenes.sistema_examenes_backend.service;


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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @InjectMocks
    private CitaServiceImpl citaService;

    private Cita citaGlobal;
    private Usuario usuarioGlobal;
    private Recurso recursoGlobal;
    private Servicio servicioGlobal;
    private Usuario empleadoGlobal;
    private DiaFestivo diaFestivoGlobal;
    private HorarioLaboral horarioLunes;

    private Set<UsuarioRol> usuarioRoles;

    @BeforeEach
    public void setup() {


        usuarioGlobal = new Usuario();
        usuarioGlobal.setId(1L);
        usuarioGlobal.setUsername("usuarioEjemplo");
        usuarioGlobal.setEmail("usuario@example.com");
        usuarioGlobal.setPassword("contraseña");
        usuarioGlobal.setNit("123456789");
        usuarioGlobal.setCui("987654321");

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
        empleadoGlobal.setNombre("Ana");
        empleadoGlobal.setApellido("Gómez");
        empleadoGlobal.setUsername("anagomez");
        empleadoGlobal.setPassword("password");
        empleadoGlobal.setEmail("ana.gomez@example.com");
        empleadoGlobal.setNit("789987987");
        empleadoGlobal.setCui("44565465");

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
        citaGlobal.setHoraInicio(LocalTime.of(13, 0));
        //citaGlobal.setHoraFin(LocalTime.of(11, 0));
        //citaGlobal.setEstado(EstadoCita.AGENDADA);
        citaGlobal.setCliente(usuarioGlobal);
        citaGlobal.setEmpleado(empleadoGlobal);
        citaGlobal.setRecurso(recursoGlobal);
        citaGlobal.setServicio(servicioGlobal);
        //citaRepository.save(citaGlobal);
    }

    @DisplayName("Test para crear una cita")
    @Test
    public void testCrearCita(){
        //given
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        citaGlobal.setHoraFin(citaGlobal.getHoraInicio().plusMinutes(servicioGlobal.getDuracionServicio()));
        DiaSemana diaSemana = DiaSemanaConverter.convertirADiaSemana(citaGlobal.getFecha().getDayOfWeek());
        given(horarioLaboralRepository.findByDia(diaSemana)).willReturn(List.of(horarioLunes));
        citaGlobal.setEstado(EstadoCita.AGENDADA);
        given(citaRepository.save(citaGlobal)).willReturn(citaGlobal);

        //when
        Cita citaGuardada = citaService.crearCita(citaGlobal);

        //then
        assertThat(citaGuardada).isNotNull();
        verify(citaRepository, times(1)).save(citaGlobal);
    }





}
