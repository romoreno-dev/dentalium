import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './pages/login/login.component';
import {NgModule} from '@angular/core';
import {DashboardComponent} from './pages/dashboard/dashboard.component';
import {dashboardRoutes} from './pages/dashboard/dashboard.routes';
import {AuthGuardService} from './auth-guard.service';

export const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'dashboard', component: DashboardComponent, children: dashboardRoutes, canActivate: [AuthGuardService]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
