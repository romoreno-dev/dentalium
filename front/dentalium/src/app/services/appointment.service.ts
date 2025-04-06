import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {SearchAppointmentRequest} from '../model/api/SearchAppointmentRequest';
import {Observable} from 'rxjs';
import {SearchAppointmentResponse} from '../model/api/SearchAppointmentResponse';
import {environment} from '../../environment/environment';
import {ModifyStatusAppointmentRequest} from '../model/api/ModifyStatusAppointmentRequest';
import {SaveAppointmentRequest} from '../model/api/SaveAppointmentRequest';
import {ModifyAppointmentRequest} from '../model/api/ModifyAppointmentRequest';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http: HttpClient) {
  }

  searchAppointments(request: SearchAppointmentRequest): Observable<SearchAppointmentResponse> {
    return this.http.post<SearchAppointmentResponse>(environment.apiUrl + "/appointment/get", request)
  }

  generateAppointmentCertificate(id: number, signed: boolean): Observable<HttpResponse<Blob>> {
    return this.http.get(environment.apiUrl + `/appointment/certificate/${id}?signed=${signed}`, {
      responseType: 'blob',
      observe: 'response'
    });
  }

  modifyStatus(request: ModifyStatusAppointmentRequest): Observable<string>  {
    return this.http.post(environment.apiUrl + `/appointment/status/modify`, request, {
      responseType: 'text'
    })
  }

  deleteAppointment(id: number): Observable<boolean>  {
    return this.http.delete<boolean>(environment.apiUrl + `/appointment/${id}`);
  }

  saveAppointment(request: SaveAppointmentRequest): Observable<Boolean> {
    return this.http.post<Boolean>(environment.apiUrl + "/appointment/save", request)
  }

  modifyAppointment(request: ModifyAppointmentRequest): Observable<Boolean> {
    return this.http.post<Boolean>(environment.apiUrl + "/appointment/save", request)
  }

}
