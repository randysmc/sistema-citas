import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baserUrl from './helper';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(
    private http:HttpClient
  ) { }

  //generamos el token
  public generateToken(loginData:any){
    return this.http.post(`${baserUrl}/generate-token`,loginData);
  }

  //iniciar sesión y establecemos el token en el localstorage
  public loginUser(token:any){
    localStorage.setItem('token', token);
  }

  //metodo para comprobar si esta conectado 
  public isLoggedIn(){
    let tokenStr = localStorage.getItem('token');
    if(tokenStr == undefined || tokenStr == '' || tokenStr == null){
      return false;
    }else{
      return true;
    }
  }

  //cerrar sesión y eliminamos el usuario
  public logout(){
    localStorage.removeItem('token');
    localStorage.removeItem('user')
    return true;
  }

  //obtener token
  public getToken(){
    return localStorage.getItem('token');
  }

  //Establecemoos usuario
  public setUser(user:any){
    localStorage.setItem('user', JSON.stringify(user));
  }
  
  //obtenemos el usuario
  public getUser(){
    let userStr = localStorage.getItem('user')
    if(userStr != null){
      return JSON.parse(userStr)
    }else{
      this.logout();
      return null;
    }
  }

  //obtenemos los roles de usuario
  public getUserRole(){
    let user = this.getUser()
    return user.authorities[0].authority;
  }

  //devolvemos el actual usuario
  public getCurrentUser(){
    return this.http.get(`${baserUrl}/actual-usuario`)
  }

}
