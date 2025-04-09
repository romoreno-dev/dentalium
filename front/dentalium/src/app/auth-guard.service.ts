import { Injectable } from '@angular/core';
import {AuthService} from './services/auth.service';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private authService:AuthService, private router:Router) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {

    if (this.authService.isAuthenticated()) {
      return true; // Permite el acceso si el token existe
    } else {
      this.router.navigate(['/login']); // Redirige al login si no hay token
      return false;
    }
  }

}
