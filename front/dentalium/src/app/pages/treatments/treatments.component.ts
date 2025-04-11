import {AfterViewInit, Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BudgetWithBudgetLinesResponse} from '../../model/api/BudgetWithBudgetLineResponse';
import {TreatmentRestDTO} from '../../model/api/TreatmentRestDto';
import {ActivatedRoute} from '@angular/router';
import {BudgetService} from '../../services/budget.service';
import {TreatmentService} from '../../services/treatment.service';
import {AuthService} from '../../services/auth.service';
import {DecimalPipe} from '@angular/common';

@Component({
  selector: 'app-treatments',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    DecimalPipe
  ],
  templateUrl: './treatments.component.html',
  styleUrl: './treatments.component.css'
})
export class TreatmentsComponent implements OnInit, AfterViewInit {
  budget!: BudgetWithBudgetLinesResponse
  loading: boolean = true;
  role: string = '';
  treatments!: TreatmentRestDTO[]
  form: FormGroup = new FormGroup({
    name: new FormControl(''),
    unitPrice: new FormControl(''),
  });

  constructor(private activeRoute: ActivatedRoute, private service: BudgetService, private treatmentService: TreatmentService, private auth: AuthService) {
  }

  ngOnInit() {
    this.role = this.auth.getRole()

    this.loadTreatments();
  }

  loadTreatments() {
    var treatmentRestDto = new TreatmentRestDTO();
    this.treatmentService.getTreatments(treatmentRestDto).subscribe(response => {
      this.treatments = response.treatments.sort((a, b) => {
        return Number(!!b.active) - Number(!!a.active);
      });
      this.loading = false;
    })
  }

  onTreatmentChange(event: boolean, treatment: TreatmentRestDTO): void {
    this.treatmentService.saveTreatment(treatment).subscribe(
      (response) => {
        this.loadTreatments();
      }
    )
  }

  addTreatment() {
    const updated = this.form.value;
    var treatment = new TreatmentRestDTO();
    treatment.active = true;
    treatment.unitPrice = updated.unitPrice;
    treatment.name = updated.name;

    this.treatmentService.saveTreatment(treatment).subscribe(next => {
      this.loadTreatments();

      this.resetForm();

      const modalElement = document.getElementById('close-modal-budget-line');
      if (modalElement) {
        modalElement.click();
      }
    });
  }

  ngAfterViewInit(): void {
    const modalElement = document.getElementById('addTreatmentModal');

    if (modalElement) {
      modalElement.addEventListener('hidden.bs.modal', () => {
        this.onCloseModal()
      });
    }
  }


  onCloseModal() {
    this.resetForm();
  }

  resetForm() {
    this.form.reset();
    this.form = new FormGroup({
      name: new FormControl(''),
      unitPrice: new FormControl(''),
    });
  }
}
