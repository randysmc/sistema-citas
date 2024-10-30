package com.sistema.examenes.sistema_examenes_backend.service;

import com.sistema.examenes.sistema_examenes_backend.Enums.TipoRecurso;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
import com.sistema.examenes.sistema_examenes_backend.excepciones.RecursoExistenteException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RecursoRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.RecursoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
@Transactional
public class RecursoServiceTest {

    @Mock
    private RecursoRepository recursoRepository;

    @InjectMocks
    private RecursoServiceImpl recursoService;

    private Recurso recurso;

    private Recurso recursoGlobal;

    @BeforeEach
    public void setUp() {
        recursoGlobal = new Recurso();
        recursoGlobal.setRecursoId(1L);
        recursoGlobal.setNombre("Recurso 1");
        recursoGlobal.setDescripcion("Descripción del recurso 1");
        recursoGlobal.setDisponible(true);
        recursoGlobal.setTipo(TipoRecurso.PERSONAL); // Asume que TipoRecurso.OTRO es válido
    }

    @Test
    @DisplayName("Prueba para guardar un recurso")
    public void testGuardarRecurso() {
        // Simula el comportamiento del repositorio
        given(recursoRepository.existsByNombre(recursoGlobal.getNombre())).willReturn(false);
        given(recursoRepository.save(recursoGlobal)).willReturn(recursoGlobal);

        // Llama al método
        Recurso resultado = recursoService.guardarRecurso(recursoGlobal);

        // Verifica el resultado
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo(recursoGlobal.getNombre());
    }

    @Test
    @DisplayName("Prueba para guardar un recurso que ya existe")
    public void testGuardarRecursoExistente() {
        // Simula el comportamiento del repositorio
        given(recursoRepository.existsByNombre(recursoGlobal.getNombre())).willReturn(true);

        // Verifica que se lance una excepción
        assertThatThrownBy(() -> recursoService.guardarRecurso(recursoGlobal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre del recurso ya existe.");
    }

    @Test
    @DisplayName("Prueba para obtener un recurso por ID")
    public void testObtenerRecursoPorId() {
        // Simula el comportamiento del repositorio
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));

        // Llama al método
        Optional<Recurso> resultado = recursoService.findById(recursoGlobal.getRecursoId());

        // Verifica el resultado
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo(recursoGlobal.getNombre());
    }

    @Test
    @DisplayName("Prueba para actualizar un recurso existente")
    public void testActualizarRecurso() {
        // Crea un nuevo recurso para actualizar
        Recurso recursoActualizar = new Recurso();
        recursoActualizar.setRecursoId(recursoGlobal.getRecursoId());
        recursoActualizar.setNombre("Recurso Actualizado");
        recursoActualizar.setDescripcion("Descripción actualizada");
        recursoActualizar.setDisponible(false);
        recursoActualizar.setTipo(TipoRecurso.PERSONAL); // Asume que TipoRecurso.OTRO es válido

        // Simula el comportamiento del repositorio
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));
        given(recursoRepository.existsByNombre(recursoActualizar.getNombre())).willReturn(false);
        given(recursoRepository.save(recursoGlobal)).willReturn(recursoGlobal);

        // Llama al método
        Recurso resultado = recursoService.actualizaRecurso(recursoActualizar);

        // Verifica el resultado
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Recurso Actualizado");
    }

    @Test
    @DisplayName("Prueba para actualizar un recurso con nombre existente")
    public void testActualizarRecursoExistente() {
        // Crea un nuevo recurso para actualizar
        Recurso recursoActualizar = new Recurso();
        recursoActualizar.setRecursoId(recursoGlobal.getRecursoId());
        recursoActualizar.setNombre("Recurso Existente");
        recursoActualizar.setDescripcion("Descripción actualizada");
        recursoActualizar.setDisponible(false);
        recursoActualizar.setTipo(TipoRecurso.PERSONAL); // Asume que TipoRecurso.OTRO es válido

        // Simula el comportamiento del repositorio
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));
        given(recursoRepository.existsByNombre(recursoActualizar.getNombre())).willReturn(true);

        // Verifica que se lance una excepción
        assertThatThrownBy(() -> recursoService.actualizaRecurso(recursoActualizar))
                .isInstanceOf(RecursoExistenteException.class)
                .hasMessageContaining("El nombre del recurso ya existe");
    }

    @Test
    @DisplayName("Prueba para eliminar un recurso")
    public void testEliminarRecurso() {
        // Llama al método
        recursoService.eliminarRecurso(recursoGlobal.getRecursoId());

        // Verifica que se llame a deleteById con el ID correcto
        verify(recursoRepository).deleteById(recursoGlobal.getRecursoId());
    }

    @Test
    @DisplayName("Prueba para obtener todos los recursos")
    public void testObtenerRecursos() {
        // Simula el comportamiento del repositorio
        given(recursoRepository.findAll()).willReturn(Arrays.asList(recursoGlobal));

        // Llama al método
        List<Recurso> resultados = recursoService.obtenerRecursos();

        // Verifica el resultado
        assertThat(resultados).hasSize(1);
        assertThat(resultados.get(0).getNombre()).isEqualTo(recursoGlobal.getNombre());
    }

    @Test
    @DisplayName("Prueba para obtener recursos disponibles")
    public void testObtenerRecursosDisponibles() {
        // Simula el comportamiento del repositorio
        given(recursoRepository.findByDisponible(true)).willReturn(Arrays.asList(recursoGlobal));

        // Llama al método
        List<Recurso> resultados = recursoService.obtenerRecursosDisponibles();

        // Verifica el resultado
        assertThat(resultados).hasSize(1);
        assertThat(resultados.get(0).getNombre()).isEqualTo(recursoGlobal.getNombre());
    }

    @Test
    @DisplayName("Prueba para obtener recursos no disponibles")
    public void testObtenerRecursosNoDisponibles() {
        // Simula el comportamiento del repositorio
        given(recursoRepository.findByDisponible(false)).willReturn(Arrays.asList(recursoGlobal));

        // Llama al método
        List<Recurso> resultados = recursoService.obtenerRecursosNoDisponibles();

        // Verifica el resultado
        assertThat(resultados).hasSize(1);
        assertThat(resultados.get(0).getNombre()).isEqualTo(recursoGlobal.getNombre());
    }


    @Test
    @DisplayName("Prueba para cambiar la disponibilidad de un recurso")
    public void testCambiarDisponibilidad() {
        // Simula el comportamiento del repositorio
        given(recursoRepository.findById(recursoGlobal.getRecursoId())).willReturn(Optional.of(recursoGlobal));

        // Llama al método para cambiar la disponibilidad
        recursoService.cambiarDisponibilidad(recursoGlobal.getRecursoId(), false);

        // Verifica que la disponibilidad se cambió
        assertThat(recursoGlobal.getDisponible()).isFalse();
    }


}
