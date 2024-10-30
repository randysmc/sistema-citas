// reserva.model.ts

export interface Reserva {
    reservaId: number;
    fecha: number[]; // [año, mes, día]
    horaInicio: number[]; // [hora, minuto]
    horaFin: number[]; // [hora, minuto]
    activa: boolean;
    cita: Cita;
    recurso: Recurso;
    empleado: Empleado | null;
    cliente: Cliente;
}

export interface Cita {
    idCita: number;
    fecha: number[]; // [año, mes, día]
    horaInicio: number[]; // [hora, minuto]
    horaFin: number[]; // [hora, minuto]
    estado: string;
    recurso: Recurso;
    servicio: Servicio;
    cliente: Cliente;
    empleado: Empleado | null;
}

export interface Recurso {
    recursoId: number;
    nombre: string;
    descripcion: string;
    disponible: boolean;
    tipo: string;
}

export interface Servicio {
    servicioId: number;
    nombre: string;
    descripcion: string;
    duracionServicio: number;
    precio: number;
    disponible: boolean;
}

export interface Cliente {
    id: number;
    username: string;
    password: string;
    nombre: string;
    apellido: string;
    email: string;
    telefono: string;
    enabled: boolean;
    perfil: string;
    nit: string;
    cui: string;
    tfa: boolean;
    authorities: Authority[];
    accountNonExpired: boolean;
    accountNonLocked: boolean;
    credentialsNonExpired: boolean;
}

export interface Authority {
    authority: string;
}

export interface Empleado {
    id: number;
    nombre: string;
    // Otros campos según sea necesario
}
