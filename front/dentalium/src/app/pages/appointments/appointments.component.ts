import {Component} from '@angular/core';
import {AppointmentService} from '../../services/appointment.service';
import {AuthService} from '../../services/auth.service';
import {AppointmentComponent} from '../../components/appointment/appointment.component';
import {SchedulerComponent} from '../../components/scheduler/scheduler.component';
import {TreatmentsComponent} from '../treatments/treatments.component';

@Component({
  selector: 'app-appointments',
  imports: [
    AppointmentComponent,
    SchedulerComponent,
    TreatmentsComponent
  ],
  templateUrl: './appointments.component.html',
  styleUrl: './appointments.component.css'
})
export class AppointmentsComponent {

  role: String = "";

  constructor(private auth: AuthService) {
  }

  ngOnInit(): void {
    this.role = this.auth.getRole();
  }

  getUserId(): string {
    return this.auth.getId()
  }

}




