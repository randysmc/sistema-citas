package com.sistema.examenes.sistema_examenes_backend.service;

import com.sistema.examenes.sistema_examenes_backend.Enums.DiaSemana;
import com.sistema.examenes.sistema_examenes_backend.entidades.DiaFestivo;

import com.sistema.examenes.sistema_examenes_backend.entidades.HorarioLaboral;
import com.sistema.examenes.sistema_examenes_backend.excepciones.HorarioExistenteException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.DiaFestivoRepository;

import com.sistema.examenes.sistema_examenes_backend.repositorios.HorarioLaboralRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.DiaFestivoServiceImpl;

import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.HorarioLaboralServiceImpl;
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
import java.time.LocalTime;
import java.util.*;

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
public class HorarioLaboralTest {

    @Mock
    private HorarioLaboralRepository horarioLaboralRepository;

    @InjectMocks
    private HorarioLaboralServiceImpl horarioLaboralService;

    @Test
    @DisplayName("Prueba para guardar un horario laboral sin conflictos")
    public void testGuardarHorarioSinConflictos() {
        HorarioLaboral nuevoHorario = new HorarioLaboral();
        nuevoHorario.setDia(DiaSemana.LUNES);
        nuevoHorario.setHoraInicio(LocalTime.of(9, 0));
        nuevoHorario.setHoraFin(LocalTime.of(17, 0));
        nuevoHorario.setTipoHorario("Completo");

        // Simula que no existen horarios previos
        given(horarioLaboralRepository.findAll()).willReturn(Collections.emptyList());
        given(horarioLaboralRepository.save(nuevoHorario)).willReturn(nuevoHorario);

        HorarioLaboral resultado = horarioLaboralService.guardarHorario(nuevoHorario);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getDia()).isEqualTo(DiaSemana.LUNES);
        assertThat(resultado.getHoraInicio()).isEqualTo(LocalTime.of(9, 0));
        assertThat(resultado.getHoraFin()).isEqualTo(LocalTime.of(17, 0));

        verify(horarioLaboralRepository, times(1)).save(nuevoHorario);
    }

    @Test
    @DisplayName("Prueba para intentar guardar un horario laboral que se traslapa con uno existente")
    public void testGuardarHorarioConConflicto() {
        HorarioLaboral nuevoHorario = new HorarioLaboral();
        nuevoHorario.setDia(DiaSemana.LUNES);
        nuevoHorario.setHoraInicio(LocalTime.of(10, 0));
        nuevoHorario.setHoraFin(LocalTime.of(12, 0));
        nuevoHorario.setTipoHorario("Parcial");

        HorarioLaboral horarioExistente = new HorarioLaboral();
        horarioExistente.setDia(DiaSemana.LUNES);
        horarioExistente.setHoraInicio(LocalTime.of(9, 0));
        horarioExistente.setHoraFin(LocalTime.of(11, 0));
        horarioExistente.setTipoHorario("Parcial");

        // Simula que ya existe un horario que se traslapa
        given(horarioLaboralRepository.findAll()).willReturn(Collections.singletonList(horarioExistente));

        assertThatThrownBy(() -> horarioLaboralService.guardarHorario(nuevoHorario))
                .isInstanceOf(HorarioExistenteException.class)
                .hasMessageContaining("El horario laboral se traslapa con un horario existente.");
    }

    @Test
    @DisplayName("Prueba para actualizar un horario laboral")
    public void testActualizarHorario() {
        HorarioLaboral horarioExistente = new HorarioLaboral();
        horarioExistente.setHorarioLaboralId(1L);
        horarioExistente.setDia(DiaSemana.MARTES);
        horarioExistente.setHoraInicio(LocalTime.of(8, 0));
        horarioExistente.setHoraFin(LocalTime.of(16, 0));
        horarioExistente.setTipoHorario("Completo");

        // Simula el horario a actualizar
        given(horarioLaboralRepository.save(horarioExistente)).willReturn(horarioExistente);

        HorarioLaboral resultado = horarioLaboralService.actualizarHorario(horarioExistente);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getHorarioLaboralId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Prueba para eliminar un horario laboral")
    public void testEliminarHorario() {
        Long idHorario = 1L;

        // Verifica que se llame al método deleteById con el ID correcto
        horarioLaboralService.eliminarHorario(idHorario);

        verify(horarioLaboralRepository, times(1)).deleteById(idHorario);
    }

    @Test
    @DisplayName("Prueba para obtener todos los horarios laborales")
    public void testObtenerHorarios() {
        HorarioLaboral horario1 = new HorarioLaboral();
        horario1.setDia(DiaSemana.LUNES);
        horario1.setHoraInicio(LocalTime.of(9, 0));
        horario1.setHoraFin(LocalTime.of(17, 0));
        horario1.setTipoHorario("Completo");

        HorarioLaboral horario2 = new HorarioLaboral();
        horario2.setDia(DiaSemana.MARTES);
        horario2.setHoraInicio(LocalTime.of(10, 0));
        horario2.setHoraFin(LocalTime.of(16, 0));
        horario2.setTipoHorario("Parcial");

        List<HorarioLaboral> horarios = Arrays.asList(horario1, horario2);

        // Simula la respuesta del repositorio
        given(horarioLaboralRepository.findAll()).willReturn(horarios);

        List<HorarioLaboral> resultado = horarioLaboralService.obtenerHorarios();

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isEqualTo(2);
        assertThat(resultado).contains(horario1, horario2);
    }



}
