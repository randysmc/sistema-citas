package com.sistema.examenes.sistema_examenes_backend.servicios;


import com.sistema.examenes.sistema_examenes_backend.entidades.Reserva;

import java.util.List;

public interface ReservaService {
    public Reserva crearReserva(Reserva reserva);

    public List<Reserva> obtenerReservas();

    public List<Reserva> obtenerReservasPorUsuario(Long usuarioId);

    public Reserva obtenerReservaPorId(Long id);

    public Reserva actualizaReserva(Reserva reserva);

    public Reserva cancelarReserva(Long id);

    public List<Reserva> obtenerReservasPorEmpleado(Long empleadoId);

    public List<Reserva> obtenerReservasActivas();
}
