package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import com.sistema.examenes.sistema_examenes_backend.Enums.TipoRecurso;
import com.sistema.examenes.sistema_examenes_backend.entidades.Cita;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.repositorios.CitaRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RecursoRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.ServicioRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CitaRepositoryTest {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    private Usuario usuarioGlobal;
    private Usuario empleadoGlobal;
    private Servicio servicioGlobal;
    private Recurso recursoGlobal;
    private Cita citaGlobal;

    @BeforeEach
    public void setUp() {

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
        //citaRepository.save(citaGlobal);
    }

    @DisplayName("Test para guardar una cita")
    @Test
    public void testGuardarCita() {
        // given
        Cita nuevaCita = new Cita();
        nuevaCita.setFecha(LocalDate.of(2024, 11, 1));
        nuevaCita.setHoraInicio(LocalTime.of(9, 0));
        nuevaCita.setHoraFin(LocalTime.of(10, 0));
        nuevaCita.setEstado(EstadoCita.AGENDADA);
        nuevaCita.setCliente(usuarioGlobal);
        nuevaCita.setEmpleado(empleadoGlobal);
        nuevaCita.setRecurso(recursoGlobal);
        nuevaCita.setServicio(servicioGlobal);

        // when
        Cita citaGuardada = citaRepository.save(nuevaCita);

        // then
        assertThat(citaGuardada).isNotNull();
        assertThat(citaGuardada.getIdCita()).isGreaterThan(0);
    }

    @DisplayName("Test para listar todas las citas")
    @Test
    public void testListarCitas() {
        //given
        citaRepository.save(citaGlobal);
        // when
        List<Cita> citas = citaRepository.findAll();

        // then
        assertThat(citas).isNotEmpty();
        assertThat(citas.size()).isEqualTo(1);
    }

    @DisplayName("Test para obtener cita por ID")
    @Test
    public void testObtenerCitaPorId() {
        //given
        citaRepository.save(citaGlobal);


        // when
        Cita citaDB = citaRepository.findById(citaGlobal.getIdCita()).get();

        // then
        assertThat(citaDB).isNotNull();
    }

    @DisplayName("Test para actualizar una cita")
    @Test
    public void testActualizarCita() {
        //given
        citaRepository.save(citaGlobal);

        // when
        Cita citaGuardada = citaRepository.findById(citaGlobal.getIdCita()).get();
        citaGuardada.setEstado(EstadoCita.CANCELADA);
        Cita citaActualizada = citaRepository.save(citaGuardada);

        // then
        assertThat(citaActualizada.getEstado()).isEqualTo(EstadoCita.CANCELADA);
    }

    @DisplayName("Test para eliminar una cita")
    @Test
    public void testEliminarCita() {
        //given
        citaRepository.save(citaGlobal);

        // when
        citaRepository.deleteById(citaGlobal.getIdCita());
        Optional<Cita> citaEliminada = citaRepository.findById(citaGlobal.getIdCita());

        // then
        assertThat(citaEliminada).isEmpty();
    }

    @DisplayName("Test para encontrar citas por cliente ID")
    @Test
    public void testEncontrarCitasPorClienteId() {
        // given
        citaRepository.save(citaGlobal);

        // when
        List<Cita> citasPorCliente = citaRepository.findByClienteId(usuarioGlobal.getId());

        // then
        assertThat(citasPorCliente).isNotEmpty();
        assertThat(citasPorCliente.get(0).getCliente().getId()).isEqualTo(usuarioGlobal.getId());
    }

    @DisplayName("Test para encontrar citas por empleado ID")
    @Test
    public void testEncontrarCitasPorEmpleadoId() {
        // given
        citaRepository.save(citaGlobal);

        // when
        List<Cita> citasPorEmpleado = citaRepository.findByEmpleadoId(empleadoGlobal.getId());

        // then
        assertThat(citasPorEmpleado).isNotEmpty();
        assertThat(citasPorEmpleado.get(0).getEmpleado().getId()).isEqualTo(empleadoGlobal.getId());
    }

    @DisplayName("Test para encontrar citas por estado")
    @Test
    public void testEncontrarCitasPorEstado() {
        // given
        citaRepository.save(citaGlobal);

        // when
        List<Cita> citasPorEstado = citaRepository.findByEstado(EstadoCita.AGENDADA);

        // then
        assertThat(citasPorEstado).isNotEmpty();
        assertThat(citasPorEstado.get(0).getEstado()).isEqualTo(EstadoCita.AGENDADA);
    }

    @DisplayName("Test para contar citas por cliente")
    @Test
    public void testContarCitasPorCliente() {
        // given

        citaRepository.save(citaGlobal);

        Cita cita2 = new Cita();
        cita2.setFecha(LocalDate.of(2024, 11, 1));
        cita2.setHoraInicio(LocalTime.of(9, 0));
        cita2.setHoraFin(LocalTime.of(10, 0));
        cita2.setEstado(EstadoCita.AGENDADA);
        cita2.setCliente(usuarioGlobal);
        cita2.setEmpleado(empleadoGlobal);
        cita2.setRecurso(recursoGlobal);
        cita2.setServicio(servicioGlobal);
        citaRepository.save(cita2);

        // when
        List<Map<String, Object>> result = citaRepository.contarCitasPorCliente();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).get("numeroCitas")).isEqualTo(2L); // Asumiendo que el usuario tiene 2 citas
    }

    @DisplayName("Test para obtener citas por estado")
    @Test
    public void testObtenerCitasPorEstado() {
        // given

        citaRepository.save(citaGlobal);

        Cita cita2 = new Cita();
        cita2.setFecha(LocalDate.of(2024, 11, 1));
        cita2.setHoraInicio(LocalTime.of(9, 0));
        cita2.setHoraFin(LocalTime.of(10, 0));
        cita2.setEstado(EstadoCita.CANCELADA);
        cita2.setCliente(usuarioGlobal);
        cita2.setEmpleado(empleadoGlobal);
        cita2.setRecurso(recursoGlobal);
        cita2.setServicio(servicioGlobal);
        citaRepository.save(cita2);

        // when
        List<Map<String, Object>> result = citaRepository.obtenerCitasPorEstado();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2); // Debe devolver dos estados: AGENDADA y CANCELADA
    }

    @DisplayName("Test para obtener usuario con más citas agendadas")
    @Test
    public void testObtenerUsuarioConMasCitasAgendadas() {
        // given

        citaRepository.save(citaGlobal);

        // when
        List<Map<String, Object>> result = citaRepository.obtenerUsuarioConMasCitasAgendadas(EstadoCita.AGENDADA);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).get("nombre")).isEqualTo("Ringo");
        assertThat(result.get(0).get("numeroCitas")).isEqualTo(1L); // Usuario tiene 1 cita agendada
    }

    @DisplayName("Test para obtener usuario con más citas agendadas")
    @Test
    public void testObtenerUsuarioConMasCitasCanceladas() {
        // given
        citaGlobal.setEstado(EstadoCita.CANCELADA);
        citaRepository.save(citaGlobal);

        // when
        List<Map<String, Object>> result = citaRepository.obtenerUsuarioConMasCitasCanceladas(EstadoCita.CANCELADA);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).get("nombre")).isEqualTo("Ringo");
        assertThat(result.get(0).get("numeroCitas")).isEqualTo(1L); // Usuario tiene 1 cita agendada
    }

    @DisplayName("Test para obtener las horas más solicitadas")
    @Test
    public void testFindMostRequestedHours() {
        // given
        Cita cita1 = new Cita();
        cita1.setFecha(LocalDate.of(2024, 10, 29));
        cita1.setHoraInicio(LocalTime.of(10, 0));
        cita1.setHoraFin(LocalTime.of(11, 0));
        cita1.setEstado(EstadoCita.AGENDADA);
        cita1.setCliente(usuarioGlobal);
        cita1.setEmpleado(empleadoGlobal);
        cita1.setRecurso(recursoGlobal);
        cita1.setServicio(servicioGlobal);
        citaRepository.save(cita1);

        // when
        List<Object[]> result = citaRepository.findMostRequestedHours();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)[0]).isEqualTo(10); // Hora más solicitada es la 10
        assertThat(result.get(0)[1]).isEqualTo(1L); // Solo 1 cita fue agendada a las 10:00
    }



    @DisplayName("Test para la frecuencia de uso por día de la semana")
    @Test
    public void testFindUsageFrequencyByDayOfWeek() {
        // given
        citaGlobal.setFecha(LocalDate.of(2024, 10, 29)); // Martes
        citaRepository.save(citaGlobal);

        Cita citaLunes = new Cita();
        citaLunes.setFecha(LocalDate.of(2024, 10, 28)); // Lunes
        citaLunes.setHoraInicio(LocalTime.of(11, 0));
        citaLunes.setHoraFin(LocalTime.of(12, 0));
        citaLunes.setEstado(EstadoCita.AGENDADA);
        citaLunes.setCliente(usuarioGlobal);
        citaLunes.setEmpleado(empleadoGlobal);
        citaLunes.setRecurso(recursoGlobal);
        citaLunes.setServicio(servicioGlobal);
        citaRepository.save(citaLunes);

        // when
        List<Object[]> result = citaRepository.findUsageFrequencyByDayOfWeek();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2); // Lunes y Martes

        assertThat(((Number) result.get(0)[0]).intValue()).isEqualTo(2);  // Día 2 -> Lunes
        assertThat(((Number) result.get(0)[1]).intValue()).isEqualTo(1);  // 1 cita el lunes

        assertThat(((Number) result.get(1)[0]).intValue()).isEqualTo(3);  // Día 3 -> Martes
        assertThat(((Number) result.get(1)[1]).intValue()).isEqualTo(1);  // 1 cita el martes
    }


    @DisplayName("Test para el uso de recursos")
    @Test
    public void testFindResourceUsage() {
        // given
        citaRepository.save(citaGlobal);

        Cita cita2 = new Cita();
        cita2.setFecha(LocalDate.of(2024, 10, 29));
        cita2.setHoraInicio(LocalTime.of(11, 0));
        cita2.setHoraFin(LocalTime.of(12, 0));
        cita2.setEstado(EstadoCita.AGENDADA);
        cita2.setCliente(usuarioGlobal);
        cita2.setEmpleado(empleadoGlobal);
        cita2.setRecurso(recursoGlobal);
        cita2.setServicio(servicioGlobal);
        citaRepository.save(cita2);

        // when
        List<Object[]> result = citaRepository.findResourceUsage();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1); // Solo un recurso utilizado

        assertThat(result.get(0)[0]).isEqualTo("Recurso de Ejemplo"); // Nombre del recurso
        assertThat(((Number) result.get(0)[1]).intValue()).isEqualTo(2); // 2 citas para el recurso
    }


    @DisplayName("Test para la tasa de cancelaciones por servicio")
    @Test
    public void testFindCancellationRateByService() {
        // given
        citaGlobal.setEstado(EstadoCita.CANCELADA);
        citaRepository.save(citaGlobal);

        Cita cita2 = new Cita();
        cita2.setFecha(LocalDate.of(2024, 10, 29));
        cita2.setHoraInicio(LocalTime.of(11, 0));
        cita2.setHoraFin(LocalTime.of(12, 0));
        cita2.setEstado(EstadoCita.AGENDADA);
        cita2.setCliente(usuarioGlobal);
        cita2.setEmpleado(empleadoGlobal);
        cita2.setRecurso(recursoGlobal);
        cita2.setServicio(servicioGlobal);
        citaRepository.save(cita2);

        // when
        List<Object[]> result = citaRepository.findCancellationRateByService();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1); // Un servicio

        assertThat(result.get(0)[0]).isEqualTo("Servicio de Ejemplo"); // Nombre del servicio
        assertThat(((Number) result.get(0)[1]).intValue()).isEqualTo(1); // 1 cita cancelada
        assertThat(((Number) result.get(0)[2]).intValue()).isEqualTo(2); // Total de 2 citas
    }


    @DisplayName("Test para listar todos los recursos utilizados")
    @Test
    public void testFindAllResourceUsage() {
        // given
        citaRepository.save(citaGlobal);  // Guardamos la primera cita
        Cita cita2 = new Cita();  // Creamos una nueva cita para asegurarnos de que hay más de una
        cita2.setFecha(LocalDate.of(2024, 10, 30)); // Fecha diferente, para diferenciar la cita
        cita2.setHoraInicio(LocalTime.of(10, 0));
        cita2.setHoraFin(LocalTime.of(11, 0));
        cita2.setEstado(EstadoCita.AGENDADA);
        cita2.setCliente(usuarioGlobal);
        cita2.setEmpleado(empleadoGlobal);
        cita2.setRecurso(recursoGlobal); // Usamos el mismo recurso
        cita2.setServicio(servicioGlobal);
        citaRepository.save(cita2);  // Guardamos la segunda cita

        // when
        List<Object[]> result = citaRepository.findAllResourceUsage();

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1); // Solo un recurso

        assertThat(result.get(0)[0]).isEqualTo("Recurso de Ejemplo"); // Nombre del recurso
        assertThat(((Number) result.get(0)[1]).intValue()).isEqualTo(2); // 2 citas para el recurso
    }







}
