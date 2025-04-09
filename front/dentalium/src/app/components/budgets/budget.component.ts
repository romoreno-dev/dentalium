import {Component, Input, OnInit} from '@angular/core';
import {Budget} from '../../model/api/BudgetResponse';
import {BudgetService} from '../../services/budget.service';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {DatePipe, formatDate} from '@angular/common';
import Swal from 'sweetalert2';
import {Appointment} from '../../model/api/SearchAppointmentResponse';
import {ModifyStatusAppointmentRequest} from '../../model/api/ModifyStatusAppointmentRequest';
import {BudgetRequestRestDTO} from '../../model/api/BudgetRequestRestDTO';

@Component({
  selector: 'budgets-tabs',
  imports: [
    DatePipe
  ],
  templateUrl: './budget.component.html',
  styleUrl: './budget.component.css'
})
export class BudgetComponent implements OnInit {

  @Input() patientId!: string;
  role: String = ''
  budgets: Budget[] = [];
  loading: boolean = true;

  constructor(private service: BudgetService,  private auth: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    this.role = this.auth.getRole();
    this.loadBudgets();
  }

  loadBudgets(): void {
    this.service.searchBudgets(this.patientId).subscribe(response => {
      this.budgets = response.budgets;
      this.loading = false;
    });
  }

  downloadBudget(budget: Budget, event: MouseEvent): void {
    event.stopPropagation(); // Para que no se lance el evento de la fila tambien
    this.service.downloadBudget(budget.id).subscribe(
      (response) => {
        const imageUrl = URL.createObjectURL(response.body!);
        window.open(imageUrl, '_blank');
      });
  }

  deleteBudget(budget: Budget, event: MouseEvent): void {
    event.stopPropagation(); // Para que no se lance el evento de la fila tambien
    Swal.fire({
      title: `¿Desea eliminar ${formatDate(budget.date, 'd/MM/YYYY', "es-ES")} ?`,
      icon: "warning",
      confirmButtonColor: "#dc3545",
      confirmButtonText: "ELIMINAR",
      showCancelButton: true,
      cancelButtonColor: "#198754",
      cancelButtonText: "MANTENER",
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.deleteBudget(budget.id).subscribe(
          (response) => {
            this.loadBudgets();
            Swal.fire({
              text: "Presupuesto eliminado con éxito",
              icon: "info"
            })
          }
        )
      }
    });
  }

  createBudget() {
    let budgetRequestRestDTO = new BudgetRequestRestDTO();
    budgetRequestRestDTO.idPatient = this.patientId;
    this.service.createBudget(budgetRequestRestDTO).subscribe(
      (response) => {
        this.loadBudgets();
      }
    )
  }

  goToBudgetDetail(budget: Budget): void {
    if (this.role.includes('ROLE_DENTIST')) {
      this.router.navigate(['dashboard/patients/budgets', budget.id]);
    } else if (this.role.includes('ROLE_PATIENT')) {
      this.router.navigate(['dashboard/budgets', budget.id]);
    }
  }




}
