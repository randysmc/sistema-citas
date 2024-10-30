package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;
import com.sistema.examenes.sistema_examenes_backend.repositorios.ServicioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
@Transactional
public class ServicioRepositoryTest {

    @Autowired
    private ServicioRepository servicioRepository;

    private Servicio servicioGlobal;

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada prueba
        servicioRepository.deleteAll();

        // Insertar un servicio para las pruebas
        servicioGlobal = new Servicio();
        servicioGlobal.setNombre("Servicio de Ejemplo");
        servicioGlobal.setDescripcion("Descripción del servicio de ejemplo");
        servicioGlobal.setDuracionServicio(60);
        servicioGlobal.setPrecio(BigDecimal.valueOf(100.0));
        servicioGlobal.setDisponible(true);
        servicioRepository.save(servicioGlobal);
    }

    @Test
    public void testCrearServicio() {
        Servicio nuevoServicio = new Servicio();
        nuevoServicio.setNombre("Servicio de Limpieza");
        nuevoServicio.setDescripcion("Limpieza de oficinas");
        nuevoServicio.setDuracionServicio(120);
        nuevoServicio.setPrecio(BigDecimal.valueOf(150.0));
        nuevoServicio.setDisponible(true);

        Servicio servicioGuardado = servicioRepository.save(nuevoServicio);

        assertThat(servicioGuardado.getServicioId()).isNotNull();
        assertThat(servicioGuardado.getNombre()).isEqualTo("Servicio de Limpieza");
        assertThat(servicioGuardado.getDisponible()).isTrue();
    }

    @Test
    public void testListarServicios() {
        List<Servicio> servicios = servicioRepository.findAll();
        assertThat(servicios).isNotEmpty(); // Verificar que la lista no está vacía
        assertThat(servicios.size()).isEqualTo(1); // Debe haber solo 1 servicio insertado
    }

    @Test
    public void testBuscarServicioPorId() {
        // Obtener el ID del servicio guardado
        Long idServicio = servicioGlobal.getServicioId();
        Optional<Servicio> servicioEncontrado = servicioRepository.findById(idServicio);

        // Verificar que el servicio se encontró
        assertThat(servicioEncontrado).isPresent(); // Verificar que el servicio está presente
        assertThat(servicioEncontrado.get().getNombre()).isEqualTo(servicioGlobal.getNombre()); // Comparar nombres
    }

    @Test
    public void testBuscarServicioNoExistente() {
        // Intentar buscar un servicio con un ID que no existe
        Optional<Servicio> servicioEncontrado = servicioRepository.findById(999L);
        assertThat(servicioEncontrado).isNotPresent(); // Verificar que no se encontró el servicio
    }

    @Test
    public void testActualizarServicio() {
        // Obtener el servicio existente y actualizarlo
        servicioGlobal.setNombre("Servicio Actualizado");
        servicioGlobal.setDescripcion("Descripción actualizada");
        servicioGlobal.setPrecio(BigDecimal.valueOf(120.0));
        servicioRepository.save(servicioGlobal); // Guardar los cambios

        // Verificar que los cambios se hayan guardado
        Optional<Servicio> servicioActualizado = servicioRepository.findById(servicioGlobal.getServicioId());
        assertThat(servicioActualizado).isPresent();
        assertThat(servicioActualizado.get().getNombre()).isEqualTo("Servicio Actualizado");
        assertThat(servicioActualizado.get().getDescripcion()).isEqualTo("Descripción actualizada");
        assertThat(servicioActualizado.get().getPrecio()).isEqualTo(BigDecimal.valueOf(120.0));
    }

    @Test
    public void testEliminarServicio() {
        // Eliminar el servicio
        servicioRepository.delete(servicioGlobal);

        // Verificar que el servicio ha sido eliminado
        Optional<Servicio> servicioEliminado = servicioRepository.findById(servicioGlobal.getServicioId());
        assertThat(servicioEliminado).isNotPresent(); // Verificar que ya no está presente
    }

    @Test
    public void testListarServiciosVacios() {
        // Limpiar la base de datos y verificar que la lista esté vacía
        servicioRepository.deleteAll();
        List<Servicio> servicios = servicioRepository.findAll();
        assertThat(servicios).isEmpty(); // Verificar que la lista esté vacía
    }

    @Test
    public void testExistenciaPorNombre() {
        // Verificar que existe un servicio por nombre
        boolean exists = servicioRepository.existsByNombre("Servicio de Ejemplo");
        assertThat(exists).isTrue();

        // Verificar que no existe un servicio con un nombre diferente
        exists = servicioRepository.existsByNombre("Servicio Inexistente");
        assertThat(exists).isFalse();
    }

    @Test
    public void testListarServiciosDisponibles() {
        // Crear un servicio disponible
        Servicio servicioDisponible = new Servicio();
        servicioDisponible.setNombre("Servicio Disponible");
        servicioDisponible.setDescripcion("Descripción de servicio disponible");
        servicioDisponible.setDuracionServicio(90);
        servicioDisponible.setPrecio(BigDecimal.valueOf(80.0));
        servicioDisponible.setDisponible(true);
        servicioRepository.save(servicioDisponible);

        // Crear un servicio no disponible
        Servicio servicioNoDisponible = new Servicio();
        servicioNoDisponible.setNombre("Servicio No Disponible");
        servicioNoDisponible.setDescripcion("Descripción de servicio no disponible");
        servicioNoDisponible.setDuracionServicio(60);
        servicioNoDisponible.setPrecio(BigDecimal.valueOf(50.0));
        servicioNoDisponible.setDisponible(false);
        servicioRepository.save(servicioNoDisponible);

        // Verificar que solo se listan los servicios disponibles
        List<Servicio> serviciosDisponibles = servicioRepository.findByDisponibleTrue();
        assertThat(serviciosDisponibles).hasSize(2); // Debería haber 2 servicios disponibles
        assertThat(serviciosDisponibles).contains(servicioDisponible);
        assertThat(serviciosDisponibles).doesNotContain(servicioNoDisponible);
    }

    @Test
    public void testListarServiciosNoDisponibles() {
        // Crear un servicio disponible
        Servicio servicioDisponible = new Servicio();
        servicioDisponible.setNombre("Servicio Disponible");
        servicioDisponible.setDescripcion("Descripción de servicio disponible");
        servicioDisponible.setDuracionServicio(90);
        servicioDisponible.setPrecio(BigDecimal.valueOf(80.0));
        servicioDisponible.setDisponible(true);
        servicioRepository.save(servicioDisponible);

        // Crear un servicio no disponible
        Servicio servicioNoDisponible = new Servicio();
        servicioNoDisponible.setNombre("Servicio No Disponible");
        servicioNoDisponible.setDescripcion("Descripción de servicio no disponible");
        servicioNoDisponible.setDuracionServicio(60);
        servicioNoDisponible.setPrecio(BigDecimal.valueOf(50.0));
        servicioNoDisponible.setDisponible(false);
        servicioRepository.save(servicioNoDisponible);

        // Verificar que solo se listan los servicios no disponibles
        List<Servicio> serviciosNoDisponibles = servicioRepository.findByDisponibleFalse();
        assertThat(serviciosNoDisponibles).hasSize(1); // Debería haber 1 servicio no disponible
        assertThat(serviciosNoDisponibles).contains(servicioNoDisponible);
        assertThat(serviciosNoDisponibles).doesNotContain(servicioDisponible);
    }
}
