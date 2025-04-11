import {Component} from '@angular/core';
import {StudiesComponent} from '../../components/studies/studies.component';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-studies',
  imports: [
    StudiesComponent
  ],
  templateUrl: './study.component.html',
  styleUrl: './study.component.css'
})
export class StudyComponent {

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
