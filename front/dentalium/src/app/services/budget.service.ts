import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from '../../environment/environment';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {BudgetResponse} from '../model/api/BudgetResponse';
import {AppointmentService} from './appointment.service';
import {BudgetWithBudgetLinesResponse} from '../model/api/BudgetWithBudgetLineResponse';
import {ModifyStatusAppointmentRequest} from '../model/api/ModifyStatusAppointmentRequest';
import {ModifyStatusBudgetRequest} from '../model/api/ModifyStatusBudgetRequest';
import {BudgetLineRestDTO, BudgetRequestRestDTO} from '../model/api/BudgetRequestRestDTO';

@Injectable({
  providedIn: 'root'
})
export class BudgetService {

  constructor(private http: HttpClient) {
  }

  searchBudgets(patientId: string): Observable<BudgetResponse> {
    return this.http.get<BudgetResponse>(environment.apiUrl + `/budget?patientId=${patientId}`)
  }

  createBudget(budgetRequestRestDTO: BudgetRequestRestDTO): Observable<boolean> {
    return this.http.post<boolean>(environment.apiUrl + `/budget/save`, budgetRequestRestDTO)
  }

  downloadBudget(id: number): Observable<HttpResponse<Blob>> {
    return this.http.get(environment.apiUrl + `/budget/${id}/download?signed=true`, {
      responseType: 'blob',
      observe: 'response'
    });
  }

  detailBudget(id: number): Observable<BudgetWithBudgetLinesResponse> {
    return this.http.get<BudgetWithBudgetLinesResponse>(environment.apiUrl + `/budget/${id}`)
  }

  deleteBudget(id: number): Observable<boolean> {
    return this.http.delete<boolean>(environment.apiUrl + `/budget/${id}`);
  }

  //todo
  modifyStatus(modifyStatusBudgetRequest: ModifyStatusBudgetRequest): Observable<string>  {
    return this.http.post(environment.apiUrl + `/budget/status/modify`, modifyStatusBudgetRequest, {
      responseType: 'text'
    })
  }

  deleteTreatment(idBudget: number, treatmentId: number): Observable<boolean> {
    return this.http.delete<boolean>(environment.apiUrl + `/budget/${idBudget}/content/${treatmentId}`);
  }

  includeTreatment(idBudget: number, budgetLine: BudgetLineRestDTO): Observable<boolean> {
    return this.http.post<boolean>(environment.apiUrl + `/budget/${idBudget}/content`, budgetLine);
  }

}
