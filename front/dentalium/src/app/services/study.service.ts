import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environment/environment';
import {ListMedicalStudiesResponse} from '../model/api/ListMedicalStudiesResponse';
import {DicomResponse} from '../model/api/DicomResponse';

@Injectable({
  providedIn: 'root'
})
export class StudyService {

  constructor(private http: HttpClient) {
  }

  searchStudies(patientId: string): Observable<ListMedicalStudiesResponse> {
    return this.http.get<ListMedicalStudiesResponse>(environment.apiUrl + `/study?patientId=${patientId}`)
  }

  downloadStudy(id: number): Observable<HttpResponse<Blob>> {
    return this.http.get(environment.apiUrl + `/study/${id}/download`, {
      responseType: 'blob',
      observe: 'response'
    });
  }

  downloadDicomStudy(id: number): Observable<HttpResponse<Blob>> {
    return this.http.get(environment.apiUrl + `/study/${id}/dicom`, {
      responseType: 'blob',
      observe: 'response'
    });
  }

  downloadDicomData(id: number): Observable<DicomResponse> {
    return this.http.get<DicomResponse>(environment.apiUrl + `/study/${id}/dicom/data`);
  }

  deleteStudy(id: number): Observable<boolean> {
    return this.http.delete<boolean>(environment.apiUrl + `/study/${id}`);
  }

  uploadStudy(formData: FormData) {
    return this.http.post<string>(environment.apiUrl + `/study/upload`, formData);
  }


}
