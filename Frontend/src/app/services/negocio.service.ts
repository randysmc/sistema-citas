import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baserUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class NegocioService {

  constructor(private http:HttpClient) { }

  //obtener todos los negocios
  public listarNegocios(){
    return this.http.get(`${baserUrl}/negocios/`);
  }

  //Agregar un negocio
  public agregarNegocio(negocio: any) {
    return this.http.post(`${baserUrl}/negocios/`, negocio);
  }

    // Obtener un negocio por ID
    public obtenerNegocioPorId(id: number) {
      return this.http.get(`${baserUrl}/negocios/${id}`);
    }

}
