package com.sistema.examenes.sistema_examenes_backend.repository;


import com.sistema.examenes.sistema_examenes_backend.Enums.DiaSemana;
import com.sistema.examenes.sistema_examenes_backend.entidades.HorarioLaboral;
import com.sistema.examenes.sistema_examenes_backend.repositorios.HorarioLaboralRepository;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
@Transactional
public class HorarioLaboralRepositoryTest {

    @Autowired
    private HorarioLaboralRepository horarioLaboralRepository;

    private HorarioLaboral horarioGlobal;

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada prueba
        horarioLaboralRepository.deleteAll();

        // Insertar un horario laboral para las pruebas
        horarioGlobal = new HorarioLaboral();
        horarioGlobal.setDia(DiaSemana.LUNES);
        horarioGlobal.setHoraInicio(LocalTime.parse("09:00"));
        horarioGlobal.setHoraFin(LocalTime.parse("17:00"));
        horarioLaboralRepository.save(horarioGlobal);
    }

    @Test
    public void testCrearHorarioLaboral() {
        HorarioLaboral nuevoHorario = new HorarioLaboral();
        nuevoHorario.setDia(DiaSemana.MARTES);
        nuevoHorario.setHoraInicio(LocalTime.parse("10:00")); // Usamos el horario esperado
        nuevoHorario.setHoraFin(LocalTime.parse("17:00"));

        HorarioLaboral horarioGuardado = horarioLaboralRepository.save(nuevoHorario);

        assertThat(horarioGuardado.getHorarioLaboralId()).isNotNull();
        assertThat(horarioGuardado.getDia()).isEqualTo(DiaSemana.MARTES);
        assertThat(horarioGuardado.getHoraInicio()).isEqualTo(LocalTime.parse("10:00")); // Ahora debe coincidir
        assertThat(horarioGuardado.getHoraFin()).isEqualTo(LocalTime.parse("17:00"));
    }


    @Test
    public void testListarHorariosLaborales() {
        List<HorarioLaboral> horarios = horarioLaboralRepository.findAll();
        assertThat(horarios).isNotEmpty(); // Verificar que la lista no está vacía
        assertThat(horarios.size()).isEqualTo(1); // Debe haber solo 1 horario insertado
    }

    @Test
    public void testBuscarHorarioLaboralPorId() {
        // Obtener el ID del horario guardado
        Long idHorario = horarioGlobal.getHorarioLaboralId();
        Optional<HorarioLaboral> horarioEncontrado = horarioLaboralRepository.findById(idHorario);

        // Verificar que el horario se encontró
        assertThat(horarioEncontrado).isPresent(); // Verificar que el horario está presente
        assertThat(horarioEncontrado.get().getDia()).isEqualTo(horarioGlobal.getDia()); // Comparar días
    }

    @Test
    public void testBuscarHorarioLaboralNoExistente() {
        // Intentar buscar un horario con un ID que no existe
        Optional<HorarioLaboral> horarioEncontrado = horarioLaboralRepository.findById(999L);
        assertThat(horarioEncontrado).isNotPresent(); // Verificar que no se encontró el horario
    }

    @Test
    public void testActualizarHorarioLaboral() {
        // Obtener el horario existente y actualizarlo
        horarioGlobal.setDia(DiaSemana.JUEVES);
        horarioGlobal.setHoraInicio(LocalTime.parse("08:00"));
        horarioGlobal.setHoraFin(LocalTime.parse("16:00"));
        horarioLaboralRepository.save(horarioGlobal); // Guardar los cambios

        // Verificar que los cambios se hayan guardado
        Optional<HorarioLaboral> horarioActualizado = horarioLaboralRepository.findById(horarioGlobal.getHorarioLaboralId());
        assertThat(horarioActualizado).isPresent();
        assertThat(horarioActualizado.get().getDia()).isEqualTo(DiaSemana.JUEVES);
        assertThat(horarioActualizado.get().getHoraInicio()).isEqualTo("08:00");
        assertThat(horarioActualizado.get().getHoraFin()).isEqualTo("16:00");
    }

    @Test
    public void testEliminarHorarioLaboral() {
        // Eliminar el horario
        horarioLaboralRepository.delete(horarioGlobal);

        // Verificar que el horario ha sido eliminado
        Optional<HorarioLaboral> horarioEliminado = horarioLaboralRepository.findById(horarioGlobal.getHorarioLaboralId());
        assertThat(horarioEliminado).isNotPresent(); // Verificar que ya no está presente
    }

    @Test
    public void testListarHorariosLaboralesVacios() {
        // Limpiar la base de datos y verificar que la lista esté vacía
        horarioLaboralRepository.deleteAll();
        List<HorarioLaboral> horarios = horarioLaboralRepository.findAll();
        assertThat(horarios).isEmpty(); // Verificar que la lista esté vacía
    }

    @Test
    public void testBuscarHorariosPorDia() {
        // Crear horarios adicionales para la prueba
        HorarioLaboral horarioMartes = new HorarioLaboral();
        horarioMartes.setDia(DiaSemana.MARTES);
        horarioMartes.setHoraInicio(LocalTime.parse("09:00"));
        horarioMartes.setHoraFin(LocalTime.parse("17:00"));
        horarioLaboralRepository.save(horarioMartes);

        List<HorarioLaboral> horariosLunes = horarioLaboralRepository.findByDia(DiaSemana.LUNES);
        assertThat(horariosLunes).hasSize(1); // Debe haber 1 horario para LUNES
        assertThat(horariosLunes.get(0).getDia()).isEqualTo(DiaSemana.LUNES);

        List<HorarioLaboral> horariosMartes = horarioLaboralRepository.findByDia(DiaSemana.MARTES);
        assertThat(horariosMartes).hasSize(1); // Debe haber 1 horario para MARTES
        assertThat(horariosMartes.get(0).getDia()).isEqualTo(DiaSemana.MARTES);
    }
}
