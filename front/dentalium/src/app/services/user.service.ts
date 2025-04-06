import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SearchAppointmentRequest} from '../model/api/SearchAppointmentRequest';
import {Observable} from 'rxjs';
import {SearchAppointmentResponse} from '../model/api/SearchAppointmentResponse';
import {environment} from '../../environment/environment';
import {SearchUsersResponse} from '../model/api/SearchUsersResponse';
import {UserSave} from '../model/api/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  changePassword(email: string): Observable<boolean> {
    return this.http.get<boolean>(environment.apiUrl + `/user/change?email=${email}`)
  }

  deleteUser(id: number): Observable<boolean> {
    return this.http.delete<boolean>(environment.apiUrl + `/user/${id}`)
  }

  searchAllPatients(): Observable<SearchUsersResponse> {
    return this.http.get<SearchUsersResponse>(environment.apiUrl + `/user/get/patient`)
  }

  searchAllDoctors(): Observable<SearchUsersResponse> {
    return this.http.get<SearchUsersResponse>(environment.apiUrl + `/user/get/doctor`)
  }

  activateUser(active: boolean, id: number): Observable<SearchUsersResponse> {
    return this.http.get<SearchUsersResponse>(environment.apiUrl + `/user/${id}/change/activate?active=${active}`)
  }

  createUser(request: UserSave): Observable<string> {
    return this.http.post<string>(environment.apiUrl + `/user`, request)
  }

}
