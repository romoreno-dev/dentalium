import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environment/environment';
import {TreatmentResponse} from '../model/api/TreatmentResponse';
import {TreatmentRestDTO} from '../model/api/TreatmentRestDto';

@Injectable({
  providedIn: 'root'
})
export class TreatmentService {

  constructor(private http: HttpClient) {
  }

  getTreatments(treatmentRestDto: TreatmentRestDTO): Observable<TreatmentResponse> {
    return this.http.post<TreatmentResponse>(environment.apiUrl + `/treatment/get`, treatmentRestDto)
  }

  saveTreatment(treatmentRestDto: TreatmentRestDTO): Observable<boolean> {
    return this.http.post<boolean>(environment.apiUrl + `/treatment/save`, treatmentRestDto);
  }

  deleteTreatment(id: number): Observable<boolean> {
    return this.http.delete<boolean>(environment.apiUrl + `/treatment/${id}`);
  }


}
