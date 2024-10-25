package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.entidades.Reserva;
import com.sistema.examenes.sistema_examenes_backend.repositorios.ReservaRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    public ReservaRepository reservaRepository;

    @Override
    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public List<Reserva> obtenerReservas() {
        return reservaRepository.findAll();
    }

    @Override
    public List<Reserva> obtenerReservasPorUsuario(Long usuarioId) {
        return reservaRepository.findByEmpleadoId(usuarioId); // O ajusta según necesites

    }

    @Override
    public Reserva obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }

    @Override
    public Reserva actualizaReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }


    @Override
    public Reserva cancelarReserva(Long id) {
        Optional<Reserva> reservaOptional = reservaRepository.findById(id);
        if (reservaOptional.isPresent()) {
            Reserva reserva = reservaOptional.get();
            reserva.setActiva(false); // Cambia el estado a inactivo
            return reservaRepository.save(reserva);
        }
        return null; // O lanza una excepción
    }

    @Override
    public List<Reserva> obtenerReservasPorEmpleado(Long empleadoId) {
        return reservaRepository.findByEmpleadoId(empleadoId); // O ajusta según necesites
    }


    @Override
    public List<Reserva> obtenerReservasActivas() {
        return reservaRepository.findByActivaTrue();
    }


}
