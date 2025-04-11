import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {HttpResponse} from '@angular/common/http';
import Swal from 'sweetalert2';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'login-form',
  imports: [
    FormsModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  user: string = '';
  password: string = '';
  passwordFieldType: string = 'password';
  message: string = ''

  constructor(private authService: AuthService, private router: Router, private userService: UserService) {
  }

  ngOnInit(): void {
    // Si ya hay un token, redirige directamente al dashboard
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/dashboard']);
    }
  }


  login() {
    this.authService.login(this.user, this.password).subscribe({
      next: (response: HttpResponse<any>) => {

        if (response.status === 200) {
          localStorage.setItem('user', this.user);
        }
        this.router.navigate(['/dashboard']);
      }, error: error => {
        if (error.status === 401) {
          this.message = 'Usuario y/o contraseña incorrectos'
        } else {
          this.message = `Ha sucedido un error`
          console.log(JSON.stringify(error))
        }
      }
    });
  }

  showPassword(event: Event): void {
    const checkbox = event.target as HTMLInputElement;
    this.passwordFieldType = checkbox.checked ? 'text' : 'password';
  }

  forgotPassword() {
    Swal.fire({
      title: 'Introduce tu correo electrónico',
      input: 'text',
      showCancelButton: true,
      confirmButtonText: 'Enviar',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        const email = result.value;
        this.userService.changePassword(email).subscribe(response => {
          Swal.fire({
            title: 'Correo enviado',
            text: `Si existe un usuario asociado a dicha cuenta, se enviará correo de recuperación`,
            icon: 'success'
          });
        })
      }
    });
  }


}
