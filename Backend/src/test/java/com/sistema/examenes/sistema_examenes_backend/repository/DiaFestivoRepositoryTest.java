package com.sistema.examenes.sistema_examenes_backend.repository;


import com.sistema.examenes.sistema_examenes_backend.entidades.DiaFestivo;
import com.sistema.examenes.sistema_examenes_backend.repositorios.DiaFestivoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

@SpringBootTest
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
@Transactional
public class DiaFestivoRepositoryTest {

    @Autowired
    private DiaFestivoRepository diaFestivoRepository;

    private DiaFestivo diaFestivoGlobal;

    @BeforeEach
    public void setUp() {
        diaFestivoRepository.deleteAll();
        diaFestivoGlobal = new DiaFestivo();
        diaFestivoGlobal.setFecha(LocalDate.of(2024, 12, 25));
        diaFestivoGlobal.setDescripcion("Navidad");
        diaFestivoGlobal.setRecurrente(true);
        diaFestivoGlobal.setAnyo(2024);

        diaFestivoRepository.save(diaFestivoGlobal);
    }

    @Test
    public void testGuardarDiaFestivo() {
        DiaFestivo diaFestivo = new DiaFestivo();
        diaFestivo.setFecha(LocalDate.of(2024, 1, 1));
        diaFestivo.setDescripcion("Año Nuevo");
        diaFestivo.setRecurrente(true);
        diaFestivo.setAnyo(2024);

        DiaFestivo diaFestivoGuardado = diaFestivoRepository.save(diaFestivo);

        assertThat(diaFestivoGuardado.getDescripcion()).isEqualTo("Año Nuevo");
    }

    @Test
    public void testEncontrarPorFechaYAnyo() {
        Optional<DiaFestivo> encontrado = diaFestivoRepository.findByFechaAndAnyo(LocalDate.of(2024, 12, 25), 2024);

        encontrado.ifPresentOrElse(
                diaFestivo -> assertThat(diaFestivo.getDescripcion()).isEqualTo("Navidad"),
                () -> fail("El día festivo no fue encontrado")
        );
    }


    @Test
    public void testEncontrarPorFecha() {
        Optional<DiaFestivo> encontrado = diaFestivoRepository.findByFecha(LocalDate.of(2024, 12, 25));

        encontrado.ifPresentOrElse(
                diaFestivo -> assertThat(diaFestivo.getDescripcion()).isEqualTo("Navidad"),
                () -> fail("El día festivo no fue encontrado")
        );
    }



    @Test
    public void testEncontrarDiasFestivosNoRecurrentes() {
        // Guardar un día no recurrente
        DiaFestivo diaNoRecurrente = new DiaFestivo();
        diaNoRecurrente.setFecha(LocalDate.of(2025, 11, 1));
        diaNoRecurrente.setDescripcion("Día no recurrente");
        diaNoRecurrente.setRecurrente(false);
        diaNoRecurrente.setAnyo(2025);
        diaFestivoRepository.save(diaNoRecurrente);

        List<DiaFestivo> noRecurrentes = diaFestivoRepository.findByRecurrenteFalse();

        // Comprobar si existe un elemento con la descripción esperada
        boolean contieneDiaNoRecurrente = noRecurrentes.stream()
                .anyMatch(dia -> "Día no recurrente".equals(dia.getDescripcion()));
        assertThat(contieneDiaNoRecurrente).isTrue();
    }



    @Test
    public void testEliminarDiaFestivo() {
        Long id = diaFestivoGlobal.getIdDiaFestivo();
        diaFestivoRepository.deleteById(id);
        assertThat(diaFestivoRepository.findById(id)).isNotPresent();
    }

    @Test
    public void testActualizarDiaFestivo() {
        Long id = diaFestivoGlobal.getIdDiaFestivo();
        diaFestivoGlobal.setDescripcion("Navidad Actualizada");
        diaFestivoRepository.save(diaFestivoGlobal);

        Optional<DiaFestivo> diaFestivoActualizado = diaFestivoRepository.findById(id);

        diaFestivoActualizado.ifPresentOrElse(
                diaFestivo -> assertThat(diaFestivo.getDescripcion()).isEqualTo("Navidad Actualizada"),
                () -> fail("El día festivo actualizado no fue encontrado")
        );
    }




}
