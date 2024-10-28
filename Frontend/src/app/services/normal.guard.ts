/*import { CanActivateFn } from '@angular/router';

export const normalGuard: CanActivateFn = (route, state) => {
  return true;
};
*/

import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { LoginService } from "./login.service";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class normalGuard implements CanActivate{


    constructor(
        private loginService: LoginService,
        private router: Router
    ){

    }

    canActivate(
        route: ActivatedRouteSnapshot, 
        state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
            if(this.loginService.isLoggedIn() && this.loginService.getUserRole() == 'CLIENTE'){
                return true;
            }

            this.router.navigate(['login'])
            return false;
        
    }
}