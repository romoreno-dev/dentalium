import {AfterViewInit, Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BudgetWithBudgetLinesResponse} from '../../model/api/BudgetWithBudgetLineResponse';
import {AuthService} from '../../services/auth.service';
import Swal from 'sweetalert2';
import {UserService} from '../../services/user.service';
import {PatientService} from '../../services/patient.service';
import {User} from '../../model/api/SearchUsersResponse';
import {UserSave} from '../../model/api/User';

@Component({
  selector: 'app-users',
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit, AfterViewInit {
  budget!: BudgetWithBudgetLinesResponse
  loading: boolean = true;
  role: string = '';
  patients!: User[]
  dentists!: User[]
  dentistSelected?: string = ''
  userSelected?: string = ''
  form: FormGroup = new FormGroup({
    name: new FormControl(''),
    firstSurname: new FormControl(''),
    secondSurname: new FormControl(''),
    email: new FormControl(''),
    phone: new FormControl(''),
    user: new FormControl(''),
    identification: new FormControl(''),
    address: new FormControl(''),
    postalCode: new FormControl(''),
    municipality: new FormControl(''),
    province: new FormControl(''),
    role: new FormControl('ROLE_PATIENT')
  });

  constructor(private patientService: PatientService, private auth: AuthService, private userService: UserService) {
  }

  ngOnInit() {
    this.role = this.auth.getRole()

    this.loadPatients();
    this.loadDoctors();
    this.loading = false;
  }

  ngAfterViewInit(): void {
    const modalElement = document.getElementById('newUserModal');

    if (modalElement) {
      modalElement.addEventListener('hidden.bs.modal', () => {
        this.onCloseNewUserModal()
      });
    }
  }

  loadPatients() {
    this.userService.searchAllPatients().subscribe(response => {
      this.patients = response.users.sort((a, b) => a.firstSurname > b.firstSurname ? 1 : -1);
    })
  }

  loadDoctors() {
    this.userService.searchAllDoctors().subscribe(response => {
      this.dentists = response.users.sort((a, b) => a.firstSurname > b.firstSurname ? 1 : -1);
    })
  }

  deleteUser(id: number) {
    Swal.fire({
      title: `¿Desea eliminar al usuario con id interno ${id} ?`,
      icon: "warning",
      confirmButtonColor: "#dc3545",
      confirmButtonText: "ELIMINAR",
      showCancelButton: true,
      cancelButtonColor: "#198754",
      cancelButtonText: "MANTENER",
    }).then((result) => {
      if (result.isConfirmed) {
        this.userService.deleteUser(id).subscribe(next => {
          this.loadPatients();
          this.loadDoctors();
        });
      }
    });
  }

  sendPassword(email: string) {
    Swal.fire({
      title: `¿Desea enviar una contraseña al email ${email} ?`,
      icon: "warning",
      confirmButtonColor: "#198754",
      confirmButtonText: "ENVIAR",
      showCancelButton: true,
      cancelButtonColor: "#dc3545",
      cancelButtonText: "CANCELAR",
    }).then((result) => {
      if (result.isConfirmed) {
        this.userService.changePassword(email).subscribe(next => {
          Swal.fire({
            title: 'Correo enviado',
            text: `Se ha enviado email con la contraseña a ${email}`,
            icon: 'success'
          });
        });
      }
    });
  }

  onActiveChange(event: boolean, id: number): void {
    this.userService.activateUser(event, id).subscribe(
      (response) => {
        this.loadDoctors();
        this.loadPatients();
      }
    )
  }

  cleanAssigmentModal() {
    this.dentistSelected = undefined;
    this.userSelected = undefined;
  }

  openAssigmentModal(patient: User) {
    this.userSelected = patient.identification;
  }

  assignDentist() {
    if (this.dentistSelected) {
      this.patientService.assignDoctor(this.userSelected!!, this.dentistSelected).subscribe({
        next: response => {
          this.cleanAssigmentModal();
          const modalElement = document.getElementById('closeAssigmentButton');
          if (modalElement) {
            modalElement.click();
          }
          this.loadPatients();
          Swal.fire({
            text: `Asignación modificada con éxito`,
            icon: "success"
          });
        },
        error: error => {
          Swal.fire({
            text: `Se ha producido un error al asignar el dentista`,
            icon: "warning"
          });
        }
      });
    }
  }

  onCloseNewUserModal() {
    this.resetFormNewUserModal();
  }

  resetFormNewUserModal() {
    this.form.reset();
    this.form = new FormGroup({
      name: new FormControl(''),
      firstSurname: new FormControl(''),
      secondSurname: new FormControl(''),
      email: new FormControl(''),
      phone: new FormControl(''),
      user: new FormControl(''),
      identification: new FormControl(''),
      address: new FormControl(''),
      postalCode: new FormControl(''),
      municipality: new FormControl(''),
      province: new FormControl(''),
      role: new FormControl('ROLE_PATIENT')
    });
  }

  newUser() {
    const updated = this.form.value;

    if (updated.name == null || updated.firstSurname == null || updated.email == null
      || updated.phone == null || updated.user == null || updated.identification == null
      || (updated.role == 'ROLE_PATIENT' &&
        (updated.address == null || updated.postalCode == null || updated.municipality == null
          || updated.province == null))) {

      Swal.fire({
        text: `Por favor, introduzca todos los datos requeridos (Nombre, apellido, email, teléfono, usuario, identificación, etc.)`,
        icon: "info",
        showClass: {
          popup: ''
        },
        hideClass: {
          popup: ''
        },
      })

      return;
    }

    var user = new UserSave(
      updated.name,
      updated.firstSurname,
      updated.secondSurname,
      updated.email,
      updated.phone,
      updated.user,
      updated.identification,
      updated.address,
      updated.postalCode,
      updated.municipality,
      updated.province,
      updated.role);

    this.userService.createUser(user).subscribe(next => {
      this.loadPatients();
      this.loadDoctors();

      Swal.fire({
        text: `Usuario creado con éxito`,
        icon: "info",
        showClass: {
          popup: ''
        },
        hideClass: {
          popup: ''
        },
      }).then((result) => {
        const modalElement = document.getElementById('close-new-user-modal');
        if (modalElement) {
          modalElement.click();
        }
      })

    });
  }

}
