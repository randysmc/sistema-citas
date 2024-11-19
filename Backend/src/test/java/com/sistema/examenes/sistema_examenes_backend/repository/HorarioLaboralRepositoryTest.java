package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.Enums.DiaSemana;
import com.sistema.examenes.sistema_examenes_backend.entidades.HorarioLaboral;
import com.sistema.examenes.sistema_examenes_backend.repositorios.HorarioLaboralRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class HorarioLaboralRepositoryTest {

    @Autowired
    private HorarioLaboralRepository horarioLaboralRepository;

    private HorarioLaboral horarioLunes;

    @BeforeEach
    public void setUp() {
        // Crear un horario laboral de ejemplo para los tests
        horarioLunes = new HorarioLaboral();
        horarioLunes.setDia(DiaSemana.LUNES);
        horarioLunes.setHoraInicio(LocalTime.of(9, 0));
        horarioLunes.setHoraFin(LocalTime.of(17, 0));
        horarioLunes.setTipoHorario("Regular");

        horarioLaboralRepository.save(horarioLunes);
    }

    @Test
    public void testGuardarHorarioLaboral() {
        // given
        HorarioLaboral horarioMartes = new HorarioLaboral();
        horarioMartes.setDia(DiaSemana.MARTES);
        horarioMartes.setHoraInicio(LocalTime.of(10, 0));
        horarioMartes.setHoraFin(LocalTime.of(18, 0));
        horarioMartes.setTipoHorario("Flexible");

        // when
        HorarioLaboral horarioGuardado = horarioLaboralRepository.save(horarioMartes);

        // then
        assertThat(horarioGuardado).isNotNull();
        assertThat(horarioGuardado.getHorarioLaboralId()).isGreaterThan(0);
        assertThat(horarioGuardado.getDia()).isEqualTo(DiaSemana.MARTES);
    }

    @Test
    public void testListarTodosLosHorarios() {
        // given
        HorarioLaboral horarioMartes = new HorarioLaboral();
        horarioMartes.setDia(DiaSemana.MARTES);
        horarioMartes.setHoraInicio(LocalTime.of(10, 0));
        horarioMartes.setHoraFin(LocalTime.of(18, 0));
        horarioMartes.setTipoHorario("Flexible");

        horarioLaboralRepository.save(horarioMartes);

        // when
        List<HorarioLaboral> horarios = horarioLaboralRepository.findAll();

        // then
        assertThat(horarios).isNotEmpty();
        assertThat(horarios.size()).isGreaterThanOrEqualTo(2); // Considerando al menos el setup inicial y este horario
    }

    @Test
    public void testBuscarHorarioPorId() {
        //given
        horarioLaboralRepository.save(horarioLunes);
        // when
        HorarioLaboral horario = horarioLaboralRepository.findById(horarioLunes.getHorarioLaboralId()).orElse(null);

        // then
        assertThat(horario).isNotNull();
        assertThat(horario.getHorarioLaboralId()).isEqualTo(horarioLunes.getHorarioLaboralId());
        assertThat(horario.getDia()).isEqualTo(DiaSemana.LUNES);
    }

    @Test
    public void testBuscarHorarioPorDia() {
        //given
        horarioLaboralRepository.save(horarioLunes);

        // when
        List<HorarioLaboral> horariosLunes = horarioLaboralRepository.findByDia(DiaSemana.LUNES);

        // then
        assertThat(horariosLunes).isNotEmpty();
        assertThat(horariosLunes.get(0).getDia()).isEqualTo(DiaSemana.LUNES);
        assertThat(horariosLunes.get(0).getHoraInicio()).isEqualTo(LocalTime.of(9, 0));
        assertThat(horariosLunes.get(0).getHoraFin()).isEqualTo(LocalTime.of(17, 0));
    }

    @Test
    public void testActualizarHorarioLaboral() {
        // given
        horarioLunes.setHoraInicio(LocalTime.of(8, 0));
        horarioLunes.setHoraFin(LocalTime.of(16, 0));

        // when
        HorarioLaboral horarioActualizado = horarioLaboralRepository.save(horarioLunes);

        // then
        assertThat(horarioActualizado).isNotNull();
        assertThat(horarioActualizado.getHoraInicio()).isEqualTo(LocalTime.of(8, 0));
        assertThat(horarioActualizado.getHoraFin()).isEqualTo(LocalTime.of(16, 0));
    }

    @Test
    public void testEliminarHorarioLaboral() {
        //given
        horarioLaboralRepository.save(horarioLunes);

        // when
        horarioLaboralRepository.delete(horarioLunes);
        List<HorarioLaboral> horariosLunes = horarioLaboralRepository.findByDia(DiaSemana.LUNES);

        // then
        assertThat(horariosLunes).isEmpty();
    }
}
