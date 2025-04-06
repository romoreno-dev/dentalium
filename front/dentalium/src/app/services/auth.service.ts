import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {environment} from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient, private router:Router) { }

  login(user:string, password:string) {
    const credentials = btoa(`${user}:${password}`);

    const headers = {'Authorization': `Basic ${credentials}`}
    return this.http.get<any>(`${environment.apiUrl}/login`, {
      headers: new HttpHeaders(headers),
      observe: 'response'
    })
  }


  getHeaders() : HttpHeaders {
    let token = this.getToken()
    return new HttpHeaders({'Authorization': `${token}`})
  }

  setAuth(headers: HttpHeaders) {

    const token = headers?.get('Authorization') ?? '';
    const role = headers?.get('X-User-Role') ?? '';
    const id = headers?.get('X-User-Id') ?? '';

    localStorage.setItem('token', token);
    localStorage.setItem('role', role);
    localStorage.setItem('id', id);
  }

  getToken():string | null {
    return localStorage.getItem('token')
  }

  getRole():string {
    return <string>localStorage.getItem('role')
  }

  getUser():string {
    return <string>localStorage.getItem('user')
  }

  getId():string {
    return <string>localStorage.getItem('id')
  }

  clearAuth() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('user');
    localStorage.removeItem('id');
  }

  isAuthenticated():boolean {
    return this.getToken() !== null
  }

}
