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
public class FacturaRepositoryTest {
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

    @Autowired
    private FacturaRepository facturaRepository;

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

    private Factura crearFactura() {
        Factura factura = new Factura();
        factura.setMonto(BigDecimal.valueOf(150.00)); // Establecer un monto
        factura.setDetalleServicio("Servicio realizado"); // Establecer detalle
        factura.setFecha(LocalDate.now());
        factura.setCliente(usuarioGlobal); // Asigna el cliente
        factura.setCita(citaGlobal); // Asigna la cita

        return facturaRepository.save(factura); // Guarda y retorna la factura
    }

    @Test
    public void testCrearFactura() {
        Factura facturaGuardada = crearFactura(); // Guarda y obtiene la factura

        // Verificaciones
        assertThat(facturaGuardada).isNotNull();
        assertThat(facturaGuardada.getMonto()).isEqualTo(BigDecimal.valueOf(150.00));
        assertThat(facturaGuardada.getCliente()).isEqualTo(usuarioGlobal);
        assertThat(facturaGuardada.getCita()).isEqualTo(citaGlobal);
    }

    @Test
    public void testObtenerFacturaPorId() {
        Factura facturaGuardada = crearFactura(); // Guarda la factura

        // Obtener la factura por ID
        Factura facturaObtenida = facturaRepository.findById(facturaGuardada.getFacturaId()).orElse(null);

        // Verificaciones
        assertThat(facturaObtenida).isNotNull();
        assertThat(facturaObtenida.getFacturaId()).isEqualTo(facturaGuardada.getFacturaId());
    }

    @Test
    public void testActualizarFactura() {
        Factura facturaGuardada = crearFactura(); // Guarda la factura

        // Actualizar propiedades de la factura
        facturaGuardada.setMonto(BigDecimal.valueOf(200.00));
        facturaGuardada.setDetalleServicio("Servicio actualizado");

        // Guardar la factura actualizada
        Factura facturaActualizada = facturaRepository.save(facturaGuardada);

        // Verificaciones
        assertThat(facturaActualizada).isNotNull();
        assertThat(facturaActualizada.getMonto()).isEqualTo(BigDecimal.valueOf(200.00));
        assertThat(facturaActualizada.getDetalleServicio()).isEqualTo("Servicio actualizado");
    }

    @Test
    public void testEliminarFactura() {
        Factura facturaGuardada = crearFactura(); // Guarda la factura

        // Eliminar la factura
        facturaRepository.delete(facturaGuardada);

        // Verificar que la factura ha sido eliminada
        Factura facturaEliminada = facturaRepository.findById(facturaGuardada.getFacturaId()).orElse(null);
        assertThat(facturaEliminada).isNull();
    }
}
