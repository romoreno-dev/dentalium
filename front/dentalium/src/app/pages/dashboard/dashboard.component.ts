import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {SidebarComponent} from '../../component/sidebar/sidebar.component';

@Component({
  selector: 'app-dashboard',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterOutlet,
    SidebarComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
}
