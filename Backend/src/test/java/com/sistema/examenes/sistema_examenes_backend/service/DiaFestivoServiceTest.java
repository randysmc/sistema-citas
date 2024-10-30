package com.sistema.examenes.sistema_examenes_backend.service;

import com.sistema.examenes.sistema_examenes_backend.entidades.DiaFestivo;

import com.sistema.examenes.sistema_examenes_backend.repositorios.DiaFestivoRepository;

import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.DiaFestivoServiceImpl;

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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
@Transactional
public class DiaFestivoServiceTest {

    @Mock
    private DiaFestivoRepository diaFestivoRepository;

    @InjectMocks
    private DiaFestivoServiceImpl diaFestivoService;

    private DiaFestivo diaFestivoGlobal;

    @BeforeEach
    public void setUp() {
        diaFestivoGlobal = new DiaFestivo();
        diaFestivoGlobal.setIdDiaFestivo(1L);
        diaFestivoGlobal.setFecha(LocalDate.of(2024, 12, 25)); // Fecha ejemplo
        diaFestivoGlobal.setDescripcion("Navidad");
        diaFestivoGlobal.setRecurrente(true);
        diaFestivoGlobal.setAnyo(2024);
    }


    @Test
    @DisplayName("Prueba para encontrar un día festivo por ID")
    public void testFindById() {
        // Simula el comportamiento del repositorio
        given(diaFestivoRepository.findById(diaFestivoGlobal.getIdDiaFestivo())).willReturn(Optional.of(diaFestivoGlobal));

        // Llama al método
        Optional<DiaFestivo> resultado = diaFestivoService.findById(diaFestivoGlobal.getIdDiaFestivo());

        // Verifica el resultado
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getDescripcion()).isEqualTo(diaFestivoGlobal.getDescripcion());
    }

    @Test
    @DisplayName("Prueba para obtener todos los días festivos")
    public void testObtenerDias() {
        // Simula el comportamiento del repositorio
        given(diaFestivoRepository.findAll()).willReturn(Arrays.asList(diaFestivoGlobal));

        // Llama al método
        List<DiaFestivo> resultados = diaFestivoService.obtenerDias();

        // Verifica el resultado
        assertThat(resultados).hasSize(1);
        assertThat(resultados.get(0).getDescripcion()).isEqualTo(diaFestivoGlobal.getDescripcion());
    }

    @Test
    @DisplayName("Prueba para obtener un día festivo por ID")
    public void testObtenerDia() {
        // Simula el comportamiento del repositorio
        given(diaFestivoRepository.findById(diaFestivoGlobal.getIdDiaFestivo())).willReturn(Optional.of(diaFestivoGlobal));

        // Llama al método
        DiaFestivo resultado = diaFestivoService.obtenerDia(diaFestivoGlobal.getIdDiaFestivo());

        // Verifica el resultado
        assertThat(resultado).isNotNull();
        assertThat(resultado.getDescripcion()).isEqualTo(diaFestivoGlobal.getDescripcion());
    }

    @Test
    @DisplayName("Prueba para guardar un día festivo")
    public void testGuardarDiaFestivo() {
        // Simula el comportamiento del repositorio
        given(diaFestivoRepository.save(diaFestivoGlobal)).willReturn(diaFestivoGlobal);

        // Llama al método
        DiaFestivo resultado = diaFestivoService.guardar(diaFestivoGlobal);

        // Verifica el resultado
        assertThat(resultado).isNotNull();
        assertThat(resultado.getDescripcion()).isEqualTo(diaFestivoGlobal.getDescripcion());
    }

    @Test
    @DisplayName("Prueba para manejar excepciones al intentar guardar un día festivo recurrente existente")
    public void testGuardarDiaFestivoRecurrenteExistente() {
        // Configura el escenario donde el día festivo ya existe
        given(diaFestivoRepository.findByFecha(diaFestivoGlobal.getFecha())).willReturn(Optional.of(diaFestivoGlobal));

        // Verifica que se lance una excepción
        assertThatThrownBy(() -> diaFestivoService.guardar(diaFestivoGlobal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El día festivo recurrente ya existe para la fecha: " + diaFestivoGlobal.getFecha());
    }

    @Test
    @DisplayName("Prueba para actualizar un día festivo")
    public void testActualizarDiaFestivo() {
        // Simula el comportamiento del repositorio
        given(diaFestivoRepository.save(diaFestivoGlobal)).willReturn(diaFestivoGlobal);

        // Llama al método
        DiaFestivo resultado = diaFestivoService.actualizar(diaFestivoGlobal);

        // Verifica el resultado
        assertThat(resultado).isNotNull();
        assertThat(resultado.getDescripcion()).isEqualTo(diaFestivoGlobal.getDescripcion());
    }

    @Test
    @DisplayName("Prueba para eliminar un día festivo")
    public void testEliminarDia() {
        // Llama al método para eliminar
        diaFestivoService.eliminarDia(diaFestivoGlobal.getIdDiaFestivo());

        // Verifica que el método deleteById haya sido llamado
        given(diaFestivoRepository.findById(diaFestivoGlobal.getIdDiaFestivo())).willReturn(Optional.empty());
        Optional<DiaFestivo> resultado = diaFestivoService.findById(diaFestivoGlobal.getIdDiaFestivo());

        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Prueba para obtener días festivos recurrentes")
    public void testObtenerDiasRecurrentes() {
        // Simula el comportamiento del repositorio
        given(diaFestivoRepository.findByRecurrenteTrue()).willReturn(Arrays.asList(diaFestivoGlobal));

        // Llama al método
        List<DiaFestivo> resultados = diaFestivoService.obtenerDiasRecurrentes();

        // Verifica el resultado
        assertThat(resultados).hasSize(1);
        assertThat(resultados.get(0).getDescripcion()).isEqualTo(diaFestivoGlobal.getDescripcion());
    }

    @Test
    @DisplayName("Prueba para obtener días festivos no recurrentes")
    public void testObtenerDiasNoRecurrentes() {
        // Crea un día festivo no recurrente
        DiaFestivo diaFestivoNoRecurrente = new DiaFestivo();
        diaFestivoNoRecurrente.setIdDiaFestivo(2L);
        diaFestivoNoRecurrente.setFecha(LocalDate.of(2023, 1, 1));
        diaFestivoNoRecurrente.setDescripcion("Año Nuevo");
        diaFestivoNoRecurrente.setRecurrente(false);
        diaFestivoNoRecurrente.setAnyo(2023);

        // Simula el comportamiento del repositorio
        given(diaFestivoRepository.findByRecurrenteFalse()).willReturn(Arrays.asList(diaFestivoNoRecurrente));

        // Llama al método
        List<DiaFestivo> resultados = diaFestivoService.obtenerDiasNoRecurrentes();

        // Verifica el resultado
        assertThat(resultados).hasSize(1);
        assertThat(resultados.get(0).getDescripcion()).isEqualTo(diaFestivoNoRecurrente.getDescripcion());
    }

    @Test
    @DisplayName("Prueba para guardar un día festivo recurrente que ya existe")
    public void testGuardarDiaFestivoRecurrenteExistenteYa() {
        // Configura un día festivo recurrente
        DiaFestivo diaFestivoRecurrente = new DiaFestivo();
        diaFestivoRecurrente.setFecha(LocalDate.of(2024, 12, 25));
        diaFestivoRecurrente.setRecurrente(true);

        // Simula el comportamiento del repositorio para el día recurrente
        given(diaFestivoRepository.findByFecha(diaFestivoRecurrente.getFecha())).willReturn(Optional.of(diaFestivoGlobal));

        // Verifica que se lance una excepción al intentar guardar el día festivo recurrente
        assertThatThrownBy(() -> diaFestivoService.guardar(diaFestivoRecurrente))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El día festivo recurrente ya existe para la fecha: " + diaFestivoRecurrente.getFecha());
    }

    @Test
    @DisplayName("Prueba para guardar un día festivo no recurrente que ya existe")
    public void testGuardarDiaFestivoNoRecurrenteExistente() {
        // Configura un día festivo no recurrente
        DiaFestivo diaFestivoNoRecurrente = new DiaFestivo();
        diaFestivoNoRecurrente.setFecha(LocalDate.of(2024, 1, 1));
        diaFestivoNoRecurrente.setAnyo(2024);
        diaFestivoNoRecurrente.setRecurrente(false);

        // Simula el comportamiento del repositorio para el día no recurrente
        given(diaFestivoRepository.findByFechaAndAnyo(diaFestivoNoRecurrente.getFecha(), diaFestivoNoRecurrente.getAnyo()))
                .willReturn(Optional.of(diaFestivoGlobal));

        // Verifica que se lance una excepción al intentar guardar el día festivo no recurrente
        assertThatThrownBy(() -> diaFestivoService.guardar(diaFestivoNoRecurrente))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El día festivo ya existe para la fecha y año: " + diaFestivoNoRecurrente.getFecha() + " - " + diaFestivoNoRecurrente.getAnyo());
    }




}
