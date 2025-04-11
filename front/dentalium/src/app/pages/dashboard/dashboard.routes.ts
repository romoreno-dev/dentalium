import {Routes} from '@angular/router';
import {AppointmentsComponent} from '../appointments/appointments.component';
import {BudgetsComponent} from '../budgets/budgets.component';
import {PatientsComponent} from '../patients/patients.component';
import {StudyComponent} from '../studies/study.component';
import {UsersComponent} from '../users/users.component';
import {BudgetDetailComponent} from '../budgets/budget-detail/budget-detail.component';
import {PatientsDetailComponent} from '../patients/patients-detail/patients-detail.component';

export const dashboardRoutes: Routes = [
  {path: '', redirectTo: 'appointments', pathMatch: 'full'},
  {path: 'appointments', component: AppointmentsComponent},
  {path: 'budgets', component: BudgetsComponent},
  {path: 'budgets/:id', component: BudgetDetailComponent },
  {path: 'patients/budgets/:id', component: BudgetDetailComponent },
  {path: 'patients', component: PatientsComponent},
  {path: 'patients/:id', component: PatientsDetailComponent },
  {path: 'studies', component: StudyComponent},
  {path: 'users', component: UsersComponent},
];
