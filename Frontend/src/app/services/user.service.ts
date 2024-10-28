import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http'
import baserUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private httpClient: HttpClient
  ) { }

  public registrarUsuario(user:any){
    return this.httpClient.post(`${baserUrl}/usuarios/`, user)
  }

  public obtenerUsuarios(){
    return this.httpClient.get(`${baserUrl}/usuarios/`)
  }

  public obtenerUsuarioPorId(id: number){
    return this.httpClient.get(`${baserUrl}/usuarios/${id}`)
  }

  public actualizarUsuario(id:number, user:any){
    return this.httpClient.put(`${baserUrl}/usuarios/${id}`, user)
  }

  public obtenerUsuariosActivados(){
    return this.httpClient.get((`${baserUrl}/usuarios/activos`))
  }

  public obtenerUsuariosDesactivados(){
    return this.httpClient.get((`${baserUrl}/usuarios/desactivados`))
  }

  public habilitarUsuario(id:number){
    return this.httpClient.put(`${baserUrl}/usuarios/activar/{id}`, {})
  }

  public deshabilitarUsuario(id:number){
    return this.httpClient.put(`${baserUrl}/usuarios/desactivar/{id}`, {})
  }

  
}
