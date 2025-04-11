import {Component} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {BudgetComponent} from '../../components/budgets/budget.component';

@Component({
  selector: 'app-budgets',
  imports: [
    BudgetComponent
  ],
  templateUrl: './budgets.component.html',
  styleUrl: './budgets.component.css'
})
export class BudgetsComponent {

  role: String = "";

  constructor(private auth: AuthService) {
  }

  ngOnInit(): void {
    this.role = this.auth.getRole();
  }

  getPatientId(): string {
    return this.auth.getId()
  }

}
