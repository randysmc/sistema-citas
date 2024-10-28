import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http'
import baserUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class EmpleadoService {

  constructor(
    private httpClient: HttpClient
  ) { }

  public registrarEmpleado(user:any){
    return this.httpClient.post(`${baserUrl}/empleados/`, user)
  }

  public obtenerEmpleados(){
    return this.httpClient.get(`${baserUrl}/empleados/`)
  }

  public obtenerEmpleadoPorId(id: number){
    return this.httpClient.get(`${baserUrl}/empleados/${id}`)
  }

  public actualizarEmpleado(id:number, user:any){
    return this.httpClient.put(`${baserUrl}/empleados/${id}`, user)
  }

  public obtenerEmpleadosActivados(){
    return this.httpClient.get((`${baserUrl}/empleados/activos`))
  }

  public obtenerEmpleadosDesactivados(){
    return this.httpClient.get((`${baserUrl}/empleados/desactivados`))
  }

  public habilitarEmpleado(id:number){
    return this.httpClient.put(`${baserUrl}/empleados/activar/{id}`, {})
  }

  public deshabilitarEmpleado(id:number){
    return this.httpClient.put(`${baserUrl}/empleados/desactivar/{id}`, {})
  }
}
