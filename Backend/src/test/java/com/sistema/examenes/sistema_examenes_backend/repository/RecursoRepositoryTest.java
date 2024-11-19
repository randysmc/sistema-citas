package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.Enums.TipoRecurso;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RecursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class RecursoRepositoryTest {

    @Autowired
    private RecursoRepository recursoRepository;

    private Recurso recursoGlobal;

    @BeforeEach
    public void setup() {
        recursoRepository.deleteAll();
        recursoGlobal = new Recurso();
        recursoGlobal.setNombre("Proyector");
        recursoGlobal.setDescripcion("Proyector HD para presentaciones");
        recursoGlobal.setDisponible(true);
        recursoGlobal.setTipo(TipoRecurso.INSTALACION);
        recursoRepository.save(recursoGlobal);
    }

    @DisplayName("Test para guardar un recurso")
    @Test
    public void testGuardarRecurso() {
        // given
        Recurso recurso = new Recurso();
        recurso.setNombre("Laptop");
        recurso.setDescripcion("Laptop para uso administrativo");
        recurso.setDisponible(true);
        recurso.setTipo(TipoRecurso.PERSONAL);

        // when
        Recurso recursoGuardado = recursoRepository.save(recurso);

        // then
        assertThat(recursoGuardado).isNotNull();
        assertThat(recursoGuardado.getRecursoId()).isGreaterThan(0);
        assertThat(recursoGuardado.getNombre()).isEqualTo("Laptop");
    }

    @DisplayName("Test para listar todos los recursos")
    @Test
    public void testListarRecursos() {
        // given
        Recurso recurso2 = new Recurso();
        recurso2.setNombre("Aula A-101");
        recurso2.setDescripcion("Aula equipada para 30 personas");
        recurso2.setDisponible(false);
        recurso2.setTipo(TipoRecurso.INSTALACION);
        recursoRepository.save(recurso2);

        // when
        List<Recurso> recursos = recursoRepository.findAll();

        // then
        assertThat(recursos).isNotNull();
        assertThat(recursos.size()).isEqualTo(2); // El recurso global + recurso2
    }

    @DisplayName("Test para obtener un recurso por ID")
    @Test
    public void testObtenerRecursoPorId() {
        // given
        Long id = recursoGlobal.getRecursoId();

        // when
        Optional<Recurso> recurso = recursoRepository.findById(id);

        // then
        assertThat(recurso).isPresent();
        assertThat(recurso.get().getRecursoId()).isEqualTo(id);
        assertThat(recurso.get().getNombre()).isEqualTo("Proyector");
    }

    @DisplayName("Test para listar recursos por disponibilidad")
    @Test
    public void testListarRecursosPorDisponibilidad() {
        // given
        Recurso recursoNoDisponible = new Recurso();
        recursoNoDisponible.setNombre("Sala de reuniones");
        recursoNoDisponible.setDescripcion("Sala equipada con audio y video");
        recursoNoDisponible.setDisponible(false);
        recursoNoDisponible.setTipo(TipoRecurso.INSTALACION);
        recursoRepository.save(recursoNoDisponible);

        // when
        List<Recurso> recursosDisponibles = recursoRepository.findByDisponible(true);

        // then
        assertThat(recursosDisponibles).isNotNull();
        assertThat(recursosDisponibles.size()).isEqualTo(1);
        assertThat(recursosDisponibles.get(0).getNombre()).isEqualTo("Proyector");
    }

    @DisplayName("Test para actualizar un recurso")
    @Test
    public void testActualizarRecurso() {
        // given
        Recurso recursoGuardado = recursoRepository.findById(recursoGlobal.getRecursoId()).get();
        recursoGuardado.setNombre("Proyector actualizado");
        recursoGuardado.setDisponible(false);

        // when
        Recurso recursoActualizado = recursoRepository.save(recursoGuardado);

        // then
        assertThat(recursoActualizado.getNombre()).isEqualTo("Proyector actualizado");
        assertThat(recursoActualizado.getDisponible()).isFalse();
    }

    @DisplayName("Test para verificar si un recurso existe por nombre")
    @Test
    public void testExisteRecursoPorNombre() {
        // given
        String nombreRecurso = "Proyector";

        // when
        boolean existe = recursoRepository.existsByNombre(nombreRecurso);

        // then
        assertThat(existe).isTrue();
    }

    @DisplayName("Test para eliminar un recurso")
    @Test
    public void testEliminarRecurso() {
        // given
        Long id = recursoGlobal.getRecursoId();

        // when
        recursoRepository.deleteById(id);
        Optional<Recurso> recurso = recursoRepository.findById(id);

        // then
        assertThat(recurso).isEmpty();
    }
}
