import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http'
import baserUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private hhtpClient: HttpClient
  ) { }

  public registrarUsuario(user:any){
    return this.hhtpClient.post(`${baserUrl}/usuarios/`, user)
  }
}
