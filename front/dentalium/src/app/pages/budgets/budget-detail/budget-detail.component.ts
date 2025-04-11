import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {BudgetService} from '../../../services/budget.service';
import {ActivatedRoute} from '@angular/router';
import {BudgetLine, BudgetWithBudgetLinesResponse} from '../../../model/api/BudgetWithBudgetLineResponse';
import Swal from 'sweetalert2';
import {ModifyStatusBudgetRequest} from '../../../model/api/ModifyStatusBudgetRequest';
import {AuthService} from '../../../services/auth.service';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TreatmentService} from '../../../services/treatment.service';
import {TreatmentRestDTO} from '../../../model/api/TreatmentRestDto';
import {DatePipe} from '@angular/common';
import {TeethComponent} from '../../../components/teeth/teeth.component';
import {BudgetLineRestDTO} from '../../../model/api/BudgetRequestRestDTO';
import {AppointmentComponent} from '../../../components/appointment/appointment.component';

@Component({
  selector: 'app-budget-detail',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    DatePipe,
    TeethComponent
  ],
  templateUrl: './budget-detail.component.html',
  styleUrl: './budget-detail.component.css'
})
export class BudgetDetailComponent implements OnInit, AfterViewInit {

  budget!: BudgetWithBudgetLinesResponse
  loading: boolean = true;
  role: string = '';
  treatments!: TreatmentRestDTO[]
  form: FormGroup = new FormGroup({
    treatmentId: new FormControl(''),
    quantity: new FormControl(1),
    discount: new FormControl(0),
    teeth: new FormControl('')
  });

  menuItems = [
    {value: 1, description: 'PENDIENTE DE EMITIR'},
    {value: 2, description: 'EMITIDO'},
    {value: 3, description: 'TRATAMIENTO EN CURSO'},
    {value: 4, description: 'RECHAZADO POR PACIENTE'},
    {value: 5, description: 'ANULADO POR DOCTOR'},
    {value: 6, description: 'TRATAMIENTO FINALIZADO'},
  ];

  @ViewChild(TeethComponent) teethComponent!: TeethComponent;

  constructor(private activeRoute: ActivatedRoute, private service: BudgetService, private treatmentService: TreatmentService, private auth: AuthService) {
  }

  ngOnInit() {
    this.activeRoute.params.subscribe(params => {
      this.loadDetails(params["id"])
    })
    this.role = this.auth.getRole()

    var treatmentRestDto = new TreatmentRestDTO();
    treatmentRestDto.active = true;
    this.treatmentService.getTreatments(treatmentRestDto).subscribe(response => {
      this.treatments = response.treatments;
    })
  }

  ngAfterViewInit(): void {
    const modalElement = document.getElementById('addBudgetLineModal');

    if (modalElement) {
      modalElement.addEventListener('hidden.bs.modal', () => {
        this.onCloseModal()
      });
    }
  }

  loadDetails(id: number) {
    this.service.detailBudget(id).subscribe(response => {
      this.budget = response;
      this.loading = false;
    });
  }

  downloadBudget(budget: BudgetWithBudgetLinesResponse) {
    this.service.downloadBudget(budget.id!!).subscribe(
      (response) => {
        const imageUrl = URL.createObjectURL(response.body!);
        window.open(imageUrl, '_blank');
      });
  }

  modifyStatus(budget: BudgetWithBudgetLinesResponse, value: number): void {

    this.service.modifyStatus(new ModifyStatusBudgetRequest(budget.id!!, value)).subscribe(
      (response) => {
        this.loadDetails(this.budget.id!!);
        Swal.fire({
          text: `Estado modificado`,
          icon: "info",
          showClass: {
            popup: ''
          },
          hideClass: {
            popup: ''
          },
        })
      }
    )
  }

  deleteTreatment(budget: BudgetWithBudgetLinesResponse, treatment: BudgetLine): void {


    Swal.fire({
      title: `¿Desea eliminar ${treatment.treatmentName} ?`,
      icon: "warning",
      confirmButtonColor: "#dc3545",
      confirmButtonText: "ELIMINAR",
      showCancelButton: true,
      cancelButtonColor: "#198754",
      cancelButtonText: "MANTENER",
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.deleteTreatment(budget.id!!, treatment.treatmentId).subscribe(
          (response) => {
            this.loadDetails(this.budget.id!!);
          });
      }
    })
  }

  addBudgetLine() {
    const updated = this.form.value;
    var budgetLine = new BudgetLineRestDTO(updated.treatmentId,
      updated.quantity ? updated.quantity : 1, updated.teeth, updated.discount ? updated.discount : 0);
    this.service.includeTreatment(this.budget.id!!, budgetLine).subscribe(next=> {
      this.loadDetails(this.budget.id!!);

      this.resetForm();

      const modalElement = document.getElementById('close-modal-budget-line');
      if (modalElement) {
        modalElement.click();
      }
    });
  }


  updateTeeth(teeth: String): void {
    this.form.patchValue({
      teeth: teeth || '',
    });
  }

  onCloseModal() {
    this.resetForm();
  }

  resetForm() {
    this.form.reset();
    this.form = new FormGroup({
      treatmentId: new FormControl(''),
      quantity: new FormControl(1),
      discount: new FormControl(0),
      teeth: new FormControl('')
    });
    this.teethComponent.cleanTeethImage()
  }
}
