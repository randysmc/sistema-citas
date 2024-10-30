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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
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

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada prueba
        citaRepository.deleteAll();
        usuarioRepository.deleteAll();
        servicioRepository.deleteAll();
        recursoRepository.deleteAll();

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
    }

    @Test
    public void testCrearCitaConDatosGlobales() {
        // Crear la cita utilizando las entidades globales creadas en el `@BeforeEach`
        Cita cita = new Cita();
        cita.setFecha(LocalDate.of(2024, 10, 29));
        cita.setHoraInicio(LocalTime.of(10, 0));
        cita.setHoraFin(LocalTime.of(11, 0));
        cita.setEstado(EstadoCita.AGENDADA);
        cita.setCliente(usuarioGlobal);
        cita.setEmpleado(empleadoGlobal);
        cita.setRecurso(recursoGlobal);
        cita.setServicio(servicioGlobal);

        // Guardar la cita en el repositorio
        Cita citaGuardada = citaRepository.save(cita);

        // Verificar que la cita se ha guardado correctamente
        assertThat(citaGuardada).isNotNull();
        assertThat(citaGuardada.getCliente()).isEqualTo(usuarioGlobal);
        assertThat(citaGuardada.getEmpleado()).isEqualTo(empleadoGlobal);
        assertThat(citaGuardada.getRecurso()).isEqualTo(recursoGlobal);
        assertThat(citaGuardada.getServicio()).isEqualTo(servicioGlobal);
        assertThat(citaGuardada.getFecha()).isEqualTo(LocalDate.of(2024, 10, 29));
        assertThat(citaGuardada.getHoraInicio()).isEqualTo(LocalTime.of(10, 0));
        assertThat(citaGuardada.getHoraFin()).isEqualTo(LocalTime.of(11, 0));
        assertThat(citaGuardada.getEstado()).isEqualTo(EstadoCita.AGENDADA);
    }

    @Test
    public void testObtenerCitaPorId() {
        Cita cita = crearCitaDeEjemplo(); // Método que crea y guarda una cita
        Cita citaEncontrada = citaRepository.findById(cita.getIdCita()).orElse(null);
        assertThat(citaEncontrada).isNotNull();
        assertThat(citaEncontrada.getIdCita()).isEqualTo(cita.getIdCita());
    }

    @Test
    public void testActualizarCita() {
        Cita cita = crearCitaDeEjemplo(); // Método que crea y guarda una cita
        cita.setEstado(EstadoCita.CANCELADA);
        Cita citaActualizada = citaRepository.save(cita);
        assertThat(citaActualizada.getEstado()).isEqualTo(EstadoCita.CANCELADA);
    }

    @Test
    public void testObtenerTodasLasCitas() {
        crearCitaDeEjemplo(); // Crea una cita de ejemplo
        List<Cita> citas = citaRepository.findAll();
        assertThat(citas).isNotEmpty();
    }

    @Test
    public void testBorrarCita() {
        Cita cita = crearCitaDeEjemplo(); // Crea una cita de ejemplo
        citaRepository.delete(cita);
        assertThat(citaRepository.findById(cita.getIdCita())).isEmpty();
    }

    @Test
    public void testObtenerCitasPorEstadoCita() {
        crearCitaDeEjemplo(); // Crea una cita de ejemplo
        List<Cita> citas = citaRepository.findByEstado(EstadoCita.AGENDADA);
        assertThat(citas).isNotEmpty();
        assertThat(citas.get(0).getEstado()).isEqualTo(EstadoCita.AGENDADA);
    }

    @Test
    public void testObtenerCitasPorCliente() {
        Cita cita = crearCitaDeEjemplo(); // Crea y guarda una cita de ejemplo
        List<Cita> citas = citaRepository.findByClienteId(cita.getCliente().getId());
        assertThat(citas).isNotEmpty();
        assertThat(citas.get(0).getCliente().getId()).isEqualTo(cita.getCliente().getId());
    }

    @Test
    public void testObtenerCitasPorEmpleado() {
        Cita cita = crearCitaDeEjemplo(); // Crea y guarda una cita de ejemplo
        List<Cita> citas = citaRepository.findByEmpleadoId(cita.getEmpleado().getId());
        assertThat(citas).isNotEmpty();
        assertThat(citas.get(0).getEmpleado().getId()).isEqualTo(cita.getEmpleado().getId());
    }




    private Cita crearCitaDeEjemplo() {
        Cita cita = new Cita();
        cita.setFecha(LocalDate.of(2024, 10, 29));
        cita.setHoraInicio(LocalTime.of(10, 0));
        cita.setHoraFin(LocalTime.of(11, 0));
        cita.setEstado(EstadoCita.AGENDADA);
        cita.setCliente(usuarioGlobal);
        cita.setEmpleado(empleadoGlobal);
        cita.setRecurso(recursoGlobal);
        cita.setServicio(servicioGlobal);
        return citaRepository.save(cita); // Guarda la cita en la base de datos
    }

}
