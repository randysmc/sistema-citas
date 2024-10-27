package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.Enums.DiaSemana;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import com.sistema.examenes.sistema_examenes_backend.Enums.TipoRecurso;
import com.sistema.examenes.sistema_examenes_backend.configuraciones.DiaSemanaConverter;
import com.sistema.examenes.sistema_examenes_backend.entidades.*;
import com.sistema.examenes.sistema_examenes_backend.repositorios.*;
import com.sistema.examenes.sistema_examenes_backend.servicios.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private DiaFestivoRepository diaFestivoRepository;

    @Autowired
    private HorarioLaboralRepository horarioLaboralRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private CitaRepository citaRepository;


    @Autowired
    private EmpleadoRepository empleadoRepository;



    @Override
    public Cita crearCita(Cita cita) {
        LocalDate fecha = cita.getFecha();
        LocalTime horaInicio = cita.getHoraInicio();
        Long servicioId = cita.getServicio().getServicioId();
        Long recursoId = cita.getRecurso().getRecursoId();

        // Validación de fecha futura
        if (fecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La cita debe ser programada para una fecha futura.");
        }

        // Obtener recurso
        Recurso recurso = recursoRepository.findById(recursoId)
                .orElseThrow(() -> new IllegalArgumentException("Recurso no encontrado"));

        // Verificar tipo de recurso
        if (recurso.getTipo() == TipoRecurso.PERSONAL) {
            // Para recursos de tipo PERSONAL, verificar que haya un empleado
            if (cita.getEmpleado() == null) {
                throw new IllegalArgumentException("El empleado es obligatorio para crear una cita con un recurso personal.");
            }

            Long empleadoId = cita.getEmpleado().getId();
            // Validar si el empleado es válido
            if (!esEmpleadoValido(empleadoId)) {
                throw new IllegalArgumentException("El empleado no tiene permisos para realizar este servicio.");
            }
        } else if (recurso.getTipo() == TipoRecurso.INSTALACION) {
            // Para recursos de tipo INSTALACIÓN, no se necesita un empleado
            cita.setEmpleado(null); // Asegurarse de que no se asigna un empleado
        }

        // Validaciones existentes...
        if (esDiaFestivo(fecha)) {
            throw new IllegalArgumentException("No se pueden crear citas en días festivos.");
        }

        LocalTime horaFin = calcularHoraFin(horaInicio, servicioId);

        if (!esHorarioLaboral(fecha, horaInicio, horaFin)) {
            throw new IllegalArgumentException("No se puede crear citas fuera de horario laboral.");
        }

        if (!recursoDisponible(recursoId)) {
            throw new IllegalArgumentException("El recurso no está disponible o no existe.");
        }

        if (!servicioDisponible(servicioId)) {
            throw new IllegalArgumentException("El servicio no está disponible o no existe.");
        }

        cita.setHoraFin(horaFin);

        if (hayConflictoConReservas(cita)) {
            throw new IllegalArgumentException("El recurso o empleado ya tiene una reservación en este horario y fecha.");
        }

        cita.setEstado(EstadoCita.AGENDADA);
        Cita nuevaCita = citaRepository.save(cita);
        crearReserva(nuevaCita);

        return nuevaCita; // Retornar la nueva cita
    }



    @Override
    public List<Cita> obtenerCitas(){
        return citaRepository.findAll();
    }

    @Override
    public List<Cita> obtenerCitaPorUsuario(Long usuarioId) {
        return citaRepository.findByClienteId(usuarioId);
    }

    @Override
    public Cita obtenerCitaPorId(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada con id: " + id));
    }


    @Override
    public Cita actualizaCita(Cita cita) {
        return null;
    }


    @Override
    public Cita cancelarCita(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada con id: " + id));

        // Eliminar la reserva relacionada si existe
        // eliminarReserva(cita);

        // Cambiar el estado de la cita a CANCELADA
        cita.setEstado(EstadoCita.CANCELADA);
        return citaRepository.save(cita);
    }

    @Override
    public Cita confirmarCita(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada con id: " + id));

        // Eliminar la reserva relacionada si existe
        // eliminarReserva(cita);

        // Cambiar el estado de la cita a CANCELADA
        cita.setEstado(EstadoCita.CONFIRMADA);
        return citaRepository.save(cita);
    }


    @Override
    public List<Cita> obtenerCitaPorEmpleado(Long empleadoId) {
        return citaRepository.findByEmpleadoId(empleadoId);
    }

    private boolean esDiaFestivo(LocalDate fecha) {
        List<DiaFestivo> diasFestivos = diaFestivoRepository.findAll(); // Cambiado para obtener todos los días festivos
        return diasFestivos.stream().anyMatch(diaFestivo -> diaFestivo.getFecha().equals(fecha));
    }


    private LocalTime calcularHoraFin(LocalTime horaInicio, Long servicioId) {
        Servicio servicio = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));
        return horaInicio.plusMinutes(servicio.getDuracionServicio());
    }

    private boolean esHorarioLaboral(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        // Convertir el día de la semana a DiaSemana
        DayOfWeek dayOfWeek = fecha.getDayOfWeek();
        DiaSemana diaSemana = DiaSemanaConverter.convertirADiaSemana(dayOfWeek);

        List<HorarioLaboral> horarios = horarioLaboralRepository.findByDia(diaSemana);


        if (horarios.isEmpty()) {
            System.out.println("No hay horarios laborales definidos para este día.");
            return false; // No hay horarios laborales definidos para este día
        }

        for (HorarioLaboral horario : horarios) {
            System.out.println("Horario laboral encontrado: " + horario.getDia() +
                    " de " + horario.getHoraInicio() + " a " + horario.getHoraFin());

            // Comprobar si la cita está dentro del horario laboral
            if (!horaInicio.isBefore(horario.getHoraInicio()) && !horaFin.isAfter(horario.getHoraFin())) {
                System.out.println("La cita está dentro del horario laboral.");
                return true; // La cita está dentro del horario laboral
            }
        }
        System.out.println("La cita no está dentro de ningún horario laboral.");
        return false; // La cita no está dentro de ningún horario laboral
    }



    private boolean recursoDisponible(Long recursoId) {

        Recurso recurso = recursoRepository.findById(recursoId).orElse(null);

        return recurso != null && recurso.getDisponible();
    }

    private boolean servicioDisponible(Long servicioId){
        Servicio servicio = servicioRepository.findById(servicioId).orElse(null);

        return servicio != null && servicio.getDisponible();
    }

    private boolean esEmpleadoValido(Long empleadoId) {
        Usuario empleado = empleadoRepository.findById(empleadoId).orElse(null);

        if (empleado == null) {
            System.out.println("El empleado no existe.");
            return false;
        }

        if (!empleado.isEnabled()) {
            System.out.println("El empleado no está activo.");
            return false;
        }

        // Verificar que el empleado tenga el authority "EMPLEADO"
        boolean tieneRolEmpleado = empleado.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("EMPLEADO"));

        if (!tieneRolEmpleado) {
            System.out.println("El empleado no tiene el rol de EMPLEADO.");
            return false;
        }

        // Si pasa todas las validaciones, entonces el empleado es válido
        return true;
    }


    public Reserva crearReserva(Cita cita) {
        Reserva reserva = new Reserva();

        // Agregar logs para depuración
        System.out.println("Cita para crear reserva: " + cita);
        System.out.println("Empleado en la cita: " + cita.getEmpleado());

        reserva.setFecha(cita.getFecha());
        reserva.setHoraInicio(cita.getHoraInicio());
        reserva.setHoraFin(cita.getHoraFin());
        reserva.setActiva(true); // Siempre activa al crear una nueva reserva
        reserva.setCita(cita);
        reserva.setRecurso(cita.getRecurso());

        // Verifica si el recurso es de tipo PERSONAL
        if (cita.getRecurso().getTipo() == TipoRecurso.PERSONAL) {
            reserva.setEmpleado(cita.getEmpleado()); // Asigna el empleado si es PERSONAL
        } else {
            reserva.setEmpleado(cita.getEmpleado()); // No asignar empleado si no es PERSONAL
        }

        reserva.setCliente(cita.getCliente());

        return reservaRepository.save(reserva);
    }





    private boolean hayConflictoConReservas(Cita cita) {
        Recurso recurso = cita.getRecurso();
        LocalDate fecha = cita.getFecha();
        LocalTime horaInicio = cita.getHoraInicio();
        LocalTime horaFin = cita.getHoraFin();

        // Verifica si hay conflicto con reservas del recurso
        if (hayConflictoConReservasDelRecurso(recurso, fecha, horaInicio, horaFin)) {
            return true; // Conflicto encontrado, no se puede crear la reserva
        }

        // Si el recurso es PERSONAL, verifica conflictos con reservas del empleado
        if (recurso.getTipo() == TipoRecurso.PERSONAL) {
            return hayConflictoConReservasDelEmpleado(cita.getEmpleado(), fecha, horaInicio, horaFin);
        }

        return false; // No hay conflicto
    }

    private boolean hayConflictoConReservasDelRecurso(Recurso recurso, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        List<Reserva> reservasConflicto = reservaRepository.findByRecursoAndFecha(recurso, fecha);

        // Verifica si hay traslape en los horarios
        for (Reserva reserva : reservasConflicto) {
            LocalTime reservaHoraInicio = reserva.getHoraInicio();
            LocalTime reservaHoraFin = reserva.getHoraFin();
            boolean traslape = (horaInicio.isBefore(reservaHoraFin) && horaFin.isAfter(reservaHoraInicio));

            if (traslape && reserva.getActiva()) {
                return true; // Hay conflicto con otra reserva en el mismo horario
            }
        }

        return false; // No hay conflicto con reservas del recurso
    }

    private boolean hayConflictoConReservasDelEmpleado(Usuario empleado, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        List<Reserva> reservasEmpleado = reservaRepository.findByEmpleadoAndFecha(empleado, fecha);

        // Verifica si hay traslape en los horarios
        for (Reserva reserva : reservasEmpleado) {
            LocalTime reservaHoraInicio = reserva.getHoraInicio();
            LocalTime reservaHoraFin = reserva.getHoraFin();
            boolean traslapeEmpleado = (horaInicio.isBefore(reservaHoraFin) && horaFin.isAfter(reservaHoraInicio));

            if (traslapeEmpleado) {
                return true; // Hay conflicto, ya que el empleado ya tiene una reserva
            }
        }

        return false; // No hay conflicto con reservas del empleado
    }





}
