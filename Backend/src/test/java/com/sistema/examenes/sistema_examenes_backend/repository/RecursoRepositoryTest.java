package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
import com.sistema.examenes.sistema_examenes_backend.Enums.TipoRecurso;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RecursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
@Transactional
public class RecursoRepositoryTest {

    @Autowired
    private RecursoRepository recursoRepository;

    private Recurso recursoGlobal;

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada prueba
        recursoRepository.deleteAll();

        // Insertar un recurso para las pruebas
        recursoGlobal = new Recurso();

        recursoGlobal.setNombre("Recurso de Ejemplo");
        recursoGlobal.setDescripcion("Descripción del recurso de ejemplo");
        recursoGlobal.setDisponible(true);
        recursoGlobal.setTipo(TipoRecurso.INSTALACION); // Asegúrate de que este tipo esté definido
        recursoRepository.save(recursoGlobal);
    }

    @Test
    public void testCrearRecurso() {
        Recurso nuevoRecurso = new Recurso();
        nuevoRecurso.setNombre("Sala de Reuniones");
        nuevoRecurso.setDescripcion("Sala equipada con proyector y pizarra");
        nuevoRecurso.setDisponible(false);
        nuevoRecurso.setTipo(TipoRecurso.INSTALACION);

        Recurso recursoGuardado = recursoRepository.save(nuevoRecurso);

        assertThat(recursoGuardado.getRecursoId()).isNotNull();
        assertThat(recursoGuardado.getNombre()).isEqualTo("Sala de Reuniones");
        assertThat(recursoGuardado.getDisponible()).isFalse();
    }


    @Test
    public void testListarRecursos() {
        List<Recurso> recursos = recursoRepository.findAll();
        assertThat(recursos).isNotEmpty(); // Verificar que la lista no está vacía
        assertThat(recursos.size()).isEqualTo(1); // Debe haber solo 1 recurso insertado
    }

    @Test
    public void testBuscarRecursoPorId() {
        // Obtener el ID del recurso guardado
        Long idRecurso = recursoGlobal.getRecursoId();
        Optional<Recurso> recursoEncontrado = recursoRepository.findById(idRecurso); // Buscar por ID

        // Verificar que el recurso se encontró
        assertThat(recursoEncontrado).isPresent(); // Verificar que el recurso está presente
        assertThat(recursoEncontrado.get().getNombre()).isEqualTo(recursoGlobal.getNombre()); // Comparar nombres
    }

    @Test
    public void testBuscarRecursoNoExistente() {
        // Intentar buscar un recurso con un ID que no existe
        Optional<Recurso> recursoEncontrado = recursoRepository.findById(999L);
        assertThat(recursoEncontrado).isNotPresent(); // Verificar que no se encontró el recurso
    }


    @Test
    public void testActualizarRecurso() {
        // Obtener el recurso existente y actualizarlo
        recursoGlobal.setNombre("Recurso Actualizado");
        recursoGlobal.setDescripcion("Descripción actualizada");
        recursoRepository.save(recursoGlobal); // Guardar los cambios

        // Verificar que los cambios se hayan guardado
        Optional<Recurso> recursoActualizado = recursoRepository.findById(recursoGlobal.getRecursoId());
        assertThat(recursoActualizado).isPresent();
        assertThat(recursoActualizado.get().getNombre()).isEqualTo("Recurso Actualizado");
        assertThat(recursoActualizado.get().getDescripcion()).isEqualTo("Descripción actualizada");
    }


    @Test
    public void testEliminarRecurso() {
        // Eliminar el recurso
        recursoRepository.delete(recursoGlobal);

        // Verificar que el recurso ha sido eliminado
        Optional<Recurso> recursoEliminado = recursoRepository.findById(recursoGlobal.getRecursoId());
        assertThat(recursoEliminado).isNotPresent(); // Verificar que ya no está presente
    }



    @Test
    public void testListarRecursosVacios() {
        // Limpiar la base de datos y verificar que la lista esté vacía
        recursoRepository.deleteAll();
        List<Recurso> recursos = recursoRepository.findAll();
        assertThat(recursos).isEmpty(); // Verificar que la lista esté vacía
    }


    @Test
    public void testCrearRecursoPersonal() {
        Recurso recursoPersonal = new Recurso();
        recursoPersonal.setNombre("Personal de Soporte");
        recursoPersonal.setDescripcion("Equipo de soporte técnico");
        recursoPersonal.setDisponible(true);
        recursoPersonal.setTipo(TipoRecurso.PERSONAL);

        Recurso recursoGuardado = recursoRepository.save(recursoPersonal);

        assertThat(recursoGuardado.getRecursoId()).isNotNull();
        assertThat(recursoGuardado.getNombre()).isEqualTo("Personal de Soporte");
        assertThat(recursoGuardado.getTipo()).isEqualTo(TipoRecurso.PERSONAL);
    }

    @Test
    public void testCrearRecursoInstalacion() {
        Recurso recursoInstalacion = new Recurso();
        recursoInstalacion.setNombre("Instalación de Proyector");
        recursoInstalacion.setDescripcion("Proyector para presentaciones");
        recursoInstalacion.setDisponible(true);
        recursoInstalacion.setTipo(TipoRecurso.INSTALACION);

        Recurso recursoGuardado = recursoRepository.save(recursoInstalacion);

        assertThat(recursoGuardado.getRecursoId()).isNotNull();
        assertThat(recursoGuardado.getNombre()).isEqualTo("Instalación de Proyector");
        assertThat(recursoGuardado.getTipo()).isEqualTo(TipoRecurso.INSTALACION);
    }

    @Test
    public void testEliminarRecursosPorTipo() {
        // Crear y guardar recursos de diferentes tipos
        Recurso recursoPersonal = new Recurso();
        recursoPersonal.setNombre("Personal de Soporte");
        recursoPersonal.setDescripcion("Equipo de soporte técnico");
        recursoPersonal.setDisponible(true);
        recursoPersonal.setTipo(TipoRecurso.PERSONAL);
        recursoRepository.save(recursoPersonal);

        Recurso recursoInstalacion = new Recurso();
        recursoInstalacion.setNombre("Instalación de Proyector");
        recursoInstalacion.setDescripcion("Proyector para presentaciones");
        recursoInstalacion.setDisponible(true);
        recursoInstalacion.setTipo(TipoRecurso.INSTALACION);
        recursoRepository.save(recursoInstalacion);

        // Eliminar el recurso de tipo PERSONAL
        recursoRepository.delete(recursoPersonal);

        // Verificar que el recurso PERSONAL ha sido eliminado
        Optional<Recurso> recursoEliminado = recursoRepository.findById(recursoPersonal.getRecursoId());
        assertThat(recursoEliminado).isNotPresent();

        // Verificar que el recurso de tipo INSTALACION aún está presente
        Optional<Recurso> recursoInstalacionEncontrado = recursoRepository.findById(recursoInstalacion.getRecursoId());
        assertThat(recursoInstalacionEncontrado).isPresent();
        assertThat(recursoInstalacionEncontrado.get().getNombre()).isEqualTo("Instalación de Proyector");
    }



}
