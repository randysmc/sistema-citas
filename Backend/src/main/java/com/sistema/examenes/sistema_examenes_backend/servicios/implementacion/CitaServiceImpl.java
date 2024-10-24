package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.Enums.DiaSemana;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
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

    //@Autowired
    //private ReservaRepository reservaRepository;

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
        Long empleadoId = cita.getEmpleado().getId();
        Long clienteId = cita.getCliente().getId();

        if (cita.getEmpleado() == null) {
            throw new IllegalArgumentException("El empleado no puede ser nulo para crear una cita.");
        }



        if (esDiaFestivo(fecha)) {
            throw new IllegalArgumentException("No se pueden crear citas en días festivos.");
        }

        LocalTime horaFin = calcularHoraFin(horaInicio, servicioId);

        if(!esHorarioLaboral(fecha, horaInicio, horaFin)){
            throw new IllegalArgumentException("No se puede crear citas fuera de horario laboral");
        }

        if (!recursoDisponible(recursoId)) {
            throw new IllegalArgumentException("El recurso no está disponible o no existe.");
        }

        if(!servicioDisponible(servicioId)){
            throw  new IllegalArgumentException("El servicio no esta disponible o no existe");
        }

        if(!esEmpleadoValido(empleadoId)){
            throw new IllegalArgumentException("El empleado no tiene permisos para realizar este servicio");
        }

        cita.setHoraFin(horaFin);

        /*if (hayConflictoConReservas(cita)) {
            throw new IllegalArgumentException("El recurso, empleado o negocio ya tiene una reserva en ese horario.");
        }*/


        cita.setEstado(EstadoCita.AGENDADA);

        Cita nuevaCita = citaRepository.save(cita);

        //crearReserva(nuevaCita);

        return cita;
    }

    @Override
    public List<Cita> obtenerCitaPorUsuario(Long usuarioId) {
        return List.of();
    }

    @Override
    public List<Cita> obtenerCitas(){
        return citaRepository.findAll();
    }
    @Override
    public Cita obtenerCitaPorId(Long id) {
        return null;
    }

    @Override
    public Cita actualizaCita(Cita cita) {
        return null;
    }

    @Override
    public void cancelarCita(Long id) {

    }

    @Override
    public List<Cita> obtenerCitaPorEmpleado(Long empleadoId) {
        return List.of();
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

    /*public Reserva crearReserva(Cita cita) {
        Reserva reserva = new Reserva();

        reserva.setFecha(cita.getFecha());
        reserva.setHoraInicio(cita.getHoraInicio());
        reserva.setHoraFin(cita.getHoraFin());
        reserva.setActiva(true); // Siempre activa al crear una nueva reserva
        reserva.setCita(cita);
        reserva.setNegocio(cita.getNegocio());
        reserva.setRecurso(cita.getRecurso());
        reserva.setEmpleado(cita.getEmpleado());
        reserva.setCliente(cita.getCliente());

        return reservaRepository.save(reserva);
    }*/



    /*private boolean hayConflictoConReservas(Cita cita) {
        Negocio negocio = cita.getNegocio();
        Usuario empleado = cita.getEmpleado();
        Recurso recurso = cita.getRecurso();
        LocalDate fecha = cita.getFecha();
        LocalTime horaInicio = cita.getHoraInicio();
        LocalTime horaFin = cita.getHoraFin();

        // Busca reservas que coincidan con el negocio, recurso y fecha
        List<Reserva> reservasConflicto = reservaRepository
                .findByNegocioAndRecursoAndFecha(negocio, recurso, fecha);

        // Verifica si hay traslape en los horarios
        for (Reserva reserva : reservasConflicto) {
            LocalTime reservaHoraInicio = reserva.getHoraInicio();
            LocalTime reservaHoraFin = reserva.getHoraFin();

            // Verifica si hay traslape en los horarios
            boolean traslape = (horaInicio.isBefore(reservaHoraFin) && horaFin.isAfter(reservaHoraInicio));

            // Si hay traslape, verifica si el empleado es diferente
            if (traslape) {
                // Aseguramos que el recurso ya está ocupado por otro empleado
                if (!reserva.getEmpleado().equals(empleado)) {
                    return true; // Hay conflicto, ya que hay un traslape de horario con otro empleado
                }
            }
        }

        // Busca reservas que coincidan solo con el empleado y la fecha
        List<Reserva> reservasEmpleado = reservaRepository
                .findByNegocioAndEmpleadoAndFecha(negocio, empleado, fecha);

        for (Reserva reserva : reservasEmpleado) {
            LocalTime reservaHoraInicio = reserva.getHoraInicio();
            LocalTime reservaHoraFin = reserva.getHoraFin();

            // Verifica si hay traslape en los horarios
            boolean traslapeEmpleado = (horaInicio.isBefore(reservaHoraFin) && horaFin.isAfter(reservaHoraInicio));

            if (traslapeEmpleado) {
                return true; // Hay conflicto, ya que el empleado ya tiene una reserva
            }
        }

        return false; // No hay conflicto
    }
*/








}
