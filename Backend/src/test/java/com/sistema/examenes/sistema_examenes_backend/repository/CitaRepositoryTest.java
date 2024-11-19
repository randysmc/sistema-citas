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
        citaRepository.save(citaGlobal);
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

}
