package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoComprobante;
import com.sistema.examenes.sistema_examenes_backend.Enums.TipoRecurso;
import com.sistema.examenes.sistema_examenes_backend.entidades.*;
import com.sistema.examenes.sistema_examenes_backend.repositorios.*;
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
public class ComprobanteRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    private Usuario usuarioGlobal;
    private Usuario empleadoGlobal;
    private Servicio servicioGlobal;
    private Recurso recursoGlobal;
    private Cita citaGlobal;

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada prueba
        citaRepository.deleteAll();
        usuarioRepository.deleteAll();
        citaRepository.deleteAll();
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

        citaGlobal = new Cita();
        citaGlobal.setFecha(LocalDate.of(2024, 10, 29));
        citaGlobal.setHoraInicio(LocalTime.of(10, 0));
        citaGlobal.setHoraFin(LocalTime.of(11, 0));
        citaGlobal.setEstado(EstadoCita.AGENDADA);
        citaGlobal.setCliente(usuarioGlobal);
        citaGlobal.setEmpleado(empleadoGlobal);
        citaGlobal.setRecurso(recursoGlobal);
        citaGlobal.setServicio(servicioGlobal);

        // Guardar los datos globales
        usuarioRepository.save(usuarioGlobal);
        usuarioRepository.save(empleadoGlobal);
        recursoRepository.save(recursoGlobal);
        servicioRepository.save(servicioGlobal);
        citaGlobal = citaRepository.save(citaGlobal);
    }


    @Test
    public void testCrearComprobante() {
        // Crear el comprobante utilizando datos globales
        Comprobante comprobante = new Comprobante();
        comprobante.setFecha(LocalDate.now());
        comprobante.setHoraInicio(LocalTime.now());
        comprobante.setDescripcion("Comprobante de prueba");
        comprobante.setEstadoComprobante(EstadoComprobante.AGENDADA);
        comprobante.setCliente(usuarioGlobal); // Usar usuario global
        comprobante.setCita(citaGlobal); // Usar cita global

        // Guardar el comprobante en el repositorio
        Comprobante comprobanteGuardado = comprobanteRepository.save(comprobante);

        // Verificaciones
        assertThat(comprobanteGuardado).isNotNull();
        assertThat(comprobanteGuardado.getCita()).isEqualTo(citaGlobal);
        assertThat(comprobanteGuardado.getCliente()).isEqualTo(usuarioGlobal);
    }

    @Test
    public void testObtenerTodosLosComprobantes() {
        // Crear y guardar un comprobante para asegurarse de que haya datos
        testCrearComprobante(); // Llamar al método que crea un comprobante

        // Obtener todos los comprobantes
        List<Comprobante> comprobantes = comprobanteRepository.findAll();

        // Verificaciones
        assertThat(comprobantes).isNotEmpty();
        assertThat(comprobantes.size()).isGreaterThan(0);
    }

    @Test
    public void testObtenerComprobantePorId() {
        // Crear y guardar un comprobante
        Comprobante comprobanteGuardado = crearComprobante(); // Modifica el método para que retorne el comprobante creado

        // Obtener el comprobante por ID
        Comprobante comprobanteObtenido = comprobanteRepository.findById(comprobanteGuardado.getComprobanteId()).orElse(null);

        // Verificaciones
        assertThat(comprobanteObtenido).isNotNull();
        assertThat(comprobanteObtenido.getComprobanteId()).isEqualTo(comprobanteGuardado.getComprobanteId());
    }

    @Test
    public void testActualizarComprobante() {
        // Crear y guardar un comprobante
        Comprobante comprobanteGuardado = crearComprobante(); // Modifica el método para que retorne el comprobante creado

        // Actualizar propiedades del comprobante
        comprobanteGuardado.setDescripcion("Comprobante actualizado");
        comprobanteGuardado.setEstadoComprobante(EstadoComprobante.CANCELADA);

        // Guardar el comprobante actualizado
        Comprobante comprobanteActualizado = comprobanteRepository.save(comprobanteGuardado);

        // Verificaciones
        assertThat(comprobanteActualizado).isNotNull();
        assertThat(comprobanteActualizado.getDescripcion()).isEqualTo("Comprobante actualizado");
        assertThat(comprobanteActualizado.getEstadoComprobante()).isEqualTo(EstadoComprobante.CANCELADA);
    }

    @Test
    public void testEliminarComprobante() {
        // Crear y guardar un comprobante
        Comprobante comprobanteGuardado = crearComprobante(); // Modifica el método para que retorne el comprobante creado

        // Eliminar el comprobante
        comprobanteRepository.delete(comprobanteGuardado);

        // Verificar que el comprobante ha sido eliminado
        Comprobante comprobanteEliminado = comprobanteRepository.findById(comprobanteGuardado.getComprobanteId()).orElse(null);
        assertThat(comprobanteEliminado).isNull();
    }


    private Comprobante crearComprobante() {
        Comprobante comprobante = new Comprobante();
        comprobante.setFecha(LocalDate.now());
        comprobante.setHoraInicio(LocalTime.now());
        comprobante.setDescripcion("Comprobante de ejemplo");
        comprobante.setEstadoComprobante(EstadoComprobante.AGENDADA);
        comprobante.setCliente(usuarioGlobal); // Asegúrate de que usuarioGlobal esté inicializado
        comprobante.setCita(citaGlobal); // Asegúrate de que citaGlobal esté inicializado

        return comprobanteRepository.save(comprobante); // Guarda y retorna el comprobante
    }


}
