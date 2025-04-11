import {Component, OnInit} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {DatePipe} from '@angular/common';
import {PatientService} from '../../services/patient.service';
import {AuthService} from '../../services/auth.service';
import {Person} from '../../model/api/ListAssigmentResponse';
import {Budget} from '../../model/api/BudgetResponse';
import {Router} from '@angular/router';

@Component({
  selector: 'app-patients',
  imports: [
    ReactiveFormsModule,
    DatePipe
  ],
  templateUrl: './patients.component.html',
  styleUrl: './patients.component.css'
})
export class PatientsComponent implements OnInit {

  patientsList: Person[] = [];
  loading: boolean = true;
  constructor(private patientService: PatientService, private auth: AuthService, private router: Router) {
  }

  ngOnInit() {
    this.loadAssigment();
  }

  loadAssigment() {
    this.patientService.searchDoctorAssigment(this.auth.getId()).subscribe(response => {
      this.patientsList = response.assigmentList.sort(
        (a, b) => a.name > b.name ? 1 : -1
      );
      this.loading = false;
    });
  }

  goToPatientsDetails(patient: Person) {
    this.router.navigate(['dashboard/patients', patient.id]);
  }


}
