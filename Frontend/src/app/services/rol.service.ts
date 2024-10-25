import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baserUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class RolService {

  constructor(private http:HttpClient) { }

  public listarRoles(){
    return this.http.get(`${baserUrl}/roles/`);
  }
}
