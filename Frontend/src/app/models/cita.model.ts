export interface Cita {
    idCita: number;
    fecha: number[];
    horaInicio: number[];
    horaFin: number[];
    estado: string;
    recurso: {
      recursoId: number;
      nombre: string;
      descripcion: string;
      disponible: boolean;
      tipo: string;
    };
    servicio: {
      servicioId: number;
      nombre: string;
      descripcion: string;
      duracionServicio: number;
      precio: number;
      disponible: boolean;
    };
    cliente: {
      id: number;
      username: string;
      nombre: string;
      apellido: string;
      email: string;
      telefono: string;
    };
    empleado: null | any; // Define según tus necesidades
  }
  