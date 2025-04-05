import {Component} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router, RouterLink, RouterLinkActive} from '@angular/router';
import {NgClass} from '@angular/common';

@Component({
  selector: 'app-sidebar',
  imports: [
    RouterLink,
    NgClass,
    RouterLinkActive
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  menuItems: any[] = []
  user: string = '';

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    this.setMenu(this.authService.getRole());
    this.user = this.authService.getUser();
  }

  setMenu(role: string): void {
    const options = [
      {name: 'Agenda', route: 'appointments', icon: 'bi bi-card-checklist', roles: ['ROLE_DENTIST']},
      {name: 'Pacientes', route: 'patients', icon: 'bi bi-people-fill', roles: ['ROLE_DENTIST']},

      {name: 'Mis citas', route: 'appointments', icon: 'bi bi-calendar', roles: ['ROLE_PATIENT']},
      {name: 'Mis estudios', route: 'studies', icon: 'bi bi-camera', roles: ['ROLE_PATIENT']},
      {name: 'Mis presupuestos', route: 'budgets', icon: 'bi bi-currency-dollar', roles: ['ROLE_PATIENT']},

      {name: 'Tratamientos', route: 'appointments', icon: 'bi bi-wrench-adjustable', roles: ['ROLE_ADMIN']},
      {name: 'Usuarios', route: 'users', icon: 'bi bi-people', roles: ['ROLE_ADMIN']},
    ];

    this.menuItems = options.filter(item => item.roles.includes(role))
  }

  logout(): void {
    this.authService.clearAuth(); // Elimina el token
    this.router.navigate(['/login']); // Redirige al login
  }

}
