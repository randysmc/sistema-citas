package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.entidades.DiaFestivo;
import com.sistema.examenes.sistema_examenes_backend.repositorios.DiaFestivoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class DiaFestivoRepositoryTest {

    @Autowired
    private DiaFestivoRepository diaFestivoRepository;

    private DiaFestivo diaFestivoGlobal;

    @BeforeEach
    public void setUp() {
        diaFestivoGlobal = new DiaFestivo();
        diaFestivoGlobal.setFecha(LocalDate.of(2024, 12, 25));
        diaFestivoGlobal.setDescripcion("Navidad");
        diaFestivoGlobal.setRecurrente(true);
        diaFestivoGlobal.setAnyo(2024);

        diaFestivoRepository.save(diaFestivoGlobal);
    }

    @Test
    public void testGuardarDiaFestivo() {
        // given
        DiaFestivo diaFestivo = new DiaFestivo();
        diaFestivo.setFecha(LocalDate.of(2025, 1, 1));
        diaFestivo.setDescripcion("Año Nuevo");
        diaFestivo.setRecurrente(false);
        diaFestivo.setAnyo(2025);

        // when
        DiaFestivo diaFestivoGuardado = diaFestivoRepository.save(diaFestivo);

        // then
        assertThat(diaFestivoGuardado).isNotNull();
        assertThat(diaFestivoGuardado.getIdDiaFestivo()).isGreaterThan(0);
    }

    @Test
    public void testBuscarDiaFestivoPorFechaYAnyo() {
        //given
        diaFestivoRepository.save(diaFestivoGlobal);

        // when
        Optional<DiaFestivo> diaFestivo = diaFestivoRepository.findByFechaAndAnyo(
                diaFestivoGlobal.getFecha(),
                diaFestivoGlobal.getAnyo()
        );

        // then
        assertThat(diaFestivo).isPresent();
        assertThat(diaFestivo.get().getDescripcion()).isEqualTo("Navidad");
    }

    @Test
    public void testBuscarDiaFestivoPorFecha() {
        //given
        diaFestivoRepository.save(diaFestivoGlobal);

        // when
        Optional<DiaFestivo> diaFestivo = diaFestivoRepository.findByFecha(diaFestivoGlobal.getFecha());

        // then
        assertThat(diaFestivo).isPresent();
        assertThat(diaFestivo.get().getDescripcion()).isEqualTo("Navidad");
    }

    @Test
    public void testBuscarDiasFestivosRecurrentes() {
        //given
        diaFestivoRepository.save(diaFestivoGlobal);

        // when
        List<DiaFestivo> diasRecurrentes = diaFestivoRepository.findByRecurrenteTrue();

        // then
        assertThat(diasRecurrentes).isNotEmpty();
        assertThat(diasRecurrentes.get(0).isRecurrente()).isTrue();
        assertThat(diasRecurrentes.get(0).getDescripcion()).isEqualTo("Navidad");
    }

    @Test
    public void testBuscarDiasFestivosNoRecurrentes() {
        // given
        DiaFestivo diaNoRecurrente = new DiaFestivo();
        diaNoRecurrente.setFecha(LocalDate.of(2025, 1, 1));
        diaNoRecurrente.setDescripcion("Año Nuevo");
        diaNoRecurrente.setRecurrente(false);
        diaNoRecurrente.setAnyo(2025);
        diaFestivoRepository.save(diaNoRecurrente);

        // when
        List<DiaFestivo> diasNoRecurrentes = diaFestivoRepository.findByRecurrenteFalse();

        // then
        assertThat(diasNoRecurrentes).isNotEmpty();
        assertThat(diasNoRecurrentes.get(0).isRecurrente()).isFalse();
        assertThat(diasNoRecurrentes.get(0).getDescripcion()).isEqualTo("Año Nuevo");
    }

    @Test
    public void testEliminarDiaFestivo() {
        //given
        diaFestivoRepository.save(diaFestivoGlobal);

        // when
        diaFestivoRepository.deleteById(diaFestivoGlobal.getIdDiaFestivo());
        Optional<DiaFestivo> diaFestivoEliminado = diaFestivoRepository.findById(diaFestivoGlobal.getIdDiaFestivo());

        // then
        assertThat(diaFestivoEliminado).isEmpty();
    }
}
