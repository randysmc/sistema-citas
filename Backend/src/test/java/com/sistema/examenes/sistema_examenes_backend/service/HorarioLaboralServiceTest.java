package com.sistema.examenes.sistema_examenes_backend.service;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


import com.sistema.examenes.sistema_examenes_backend.Enums.DiaSemana;
import com.sistema.examenes.sistema_examenes_backend.entidades.HorarioLaboral;
import com.sistema.examenes.sistema_examenes_backend.entidades.Permiso;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityNotFoundException;
import com.sistema.examenes.sistema_examenes_backend.excepciones.HorarioExistenteException;
import com.sistema.examenes.sistema_examenes_backend.excepciones.PermisoExistenteException;
import com.sistema.examenes.sistema_examenes_backend.excepciones.RolExistenteException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.HorarioLaboralRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.PermisoRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.HorarioLaboralServiceImpl;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.PermisoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test") // Usa el archivo application-test.properties

public class HorarioLaboralServiceTest {

    @Mock
    private HorarioLaboralRepository horarioLaboralRepository;

    @InjectMocks
    private HorarioLaboralServiceImpl horarioLaboralService;

    private HorarioLaboral horarioLunes;

    @BeforeEach
    public void setUp() {
        // Crear un horario laboral de ejemplo para los tests
        horarioLunes = new HorarioLaboral();
        //horarioLunes.setHorarioLaboralId(1L);
        horarioLunes.setDia(DiaSemana.LUNES);
        horarioLunes.setHoraInicio(LocalTime.of(9, 0));
        horarioLunes.setHoraFin(LocalTime.of(17, 0));
        horarioLunes.setTipoHorario("Regular");

        horarioLaboralRepository.save(horarioLunes);
    }

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
                .hasMessageContaining("Ya existe un horario establecido");
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

        // Simula que el horario existe en el repositorio
        given(horarioLaboralRepository.findById(horarioExistente.getHorarioLaboralId()))
                .willReturn(Optional.of(horarioExistente));

        // Simula la actualización guardando el horario
        given(horarioLaboralRepository.save(horarioExistente)).willReturn(horarioExistente);

        // Llamada al servicio para actualizar el horario
        HorarioLaboral resultado = horarioLaboralService.actualizarHorario(horarioExistente);

        // Verificaciones
        assertThat(resultado).isNotNull();
        assertThat(resultado.getHorarioLaboralId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Prueba para actualizar un horario con un ID que no existe")
    public void testActualizarHorarioConIdNoExistente() {
        // Crear el horario con ID que no existe en la base de datos
        HorarioLaboral horarioInexistente = new HorarioLaboral();
        horarioInexistente.setHorarioLaboralId(999L);  // ID que no existe en la base de datos
        horarioInexistente.setDia(DiaSemana.LUNES);
        horarioInexistente.setHoraInicio(LocalTime.of(9, 0));
        horarioInexistente.setHoraFin(LocalTime.of(17, 0));
        horarioInexistente.setTipoHorario("Completo");

        // Simula que el horario con ese ID no existe en el repositorio (debe devolver Optional.empty())
        given(horarioLaboralRepository.findById(horarioInexistente.getHorarioLaboralId()))
                .willReturn(Optional.empty());

        // Llamada al servicio para actualizar el horario
        // Se espera que se lance la excepción IllegalArgumentException
        assertThatThrownBy(() -> horarioLaboralService.actualizarHorario(horarioInexistente))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El horario no existe.");
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




    @Test
    @DisplayName("Prueba para eliminar un horario laboral cuando existe")
    public void testEliminarHorarioExistente() {
        Long horarioLaboralId = 1L;

        // Simula que el horario con el ID 1 existe en el repositorio
        HorarioLaboral horarioExistente = new HorarioLaboral();
        horarioExistente.setHorarioLaboralId(horarioLaboralId);

        given(horarioLaboralRepository.findById(horarioLaboralId)).willReturn(Optional.of(horarioExistente));

        // Simula que el método deleteById no hace nada, ya que solo eliminamos el horario
        willDoNothing().given(horarioLaboralRepository).deleteById(horarioLaboralId);

        // Cuando llamamos al servicio para eliminar
        horarioLaboralService.eliminarHorario(horarioLaboralId);

        // Verificamos que el método deleteById fue llamado exactamente una vez
        verify(horarioLaboralRepository, times(1)).deleteById(horarioLaboralId);
    }

    @Test
    @DisplayName("Prueba para eliminar un horario laboral que no existe")
    public void testEliminarHorarioNoExistente() {
        Long horarioLaboralId = 999L;  // ID que no existe

        // Simula que el horario con el ID 999 no existe en el repositorio
        given(horarioLaboralRepository.findById(horarioLaboralId)).willReturn(Optional.empty());

        // Verificamos que se lanza la excepción
        assertThatThrownBy(() -> horarioLaboralService.eliminarHorario(horarioLaboralId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El horario laboral con ID " + horarioLaboralId + " no existe.");

        // Verificamos que deleteById no haya sido llamado
        verify(horarioLaboralRepository, times(0)).deleteById(horarioLaboralId);
    }



}
