import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environment/environment';
import {ListAssigmentResponse} from '../model/api/ListAssigmentResponse';
import {SearchUsersResponse, User} from '../model/api/SearchUsersResponse';
import {AsignDoctorRequest} from '../model/api/AsignDoctorRequest';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private http: HttpClient) {
  }

  searchDoctorAssigment(idDoctor: string): Observable<ListAssigmentResponse> {
    return this.http.get<ListAssigmentResponse>(environment.apiUrl + `/assignment?idDoctor=${idDoctor}`)
  }

  searchPatientAssigment(idPatient: string): Observable<ListAssigmentResponse> {
    return this.http.get<ListAssigmentResponse>(environment.apiUrl + `/assignment?idPatient=${idPatient}`)
  }

  searchPatientInfo(idPatient: string): Observable<SearchUsersResponse> {
    return this.http.get<SearchUsersResponse>(environment.apiUrl + `/user/get/${idPatient}`)
  }

  assignDoctor(idPatient: string, idDoctor: string): Observable<boolean> {
    let request = new AsignDoctorRequest(idPatient, idDoctor);
    return this.http.post<boolean>(environment.apiUrl + `/assignment/modify`, request)
  }



}
