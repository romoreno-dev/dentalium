import {Component, Input, OnInit} from '@angular/core';
import {DatePipe, formatDate, NgClass} from "@angular/common";
import {Appointment} from '../../model/api/SearchAppointmentResponse';
import Swal from 'sweetalert2';
import {ModifyStatusAppointmentRequest} from '../../model/api/ModifyStatusAppointmentRequest';
import {SearchAppointmentRequest} from '../../model/api/SearchAppointmentRequest';
import {AppointmentService} from '../../services/appointment.service';
import {AuthService} from '../../services/auth.service';
import {SaveAppointmentRequest} from '../../model/api/SaveAppointmentRequest';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ModifyAppointmentRequest} from '../../model/api/ModifyAppointmentRequest';
import {Person} from '../../model/api/ListAssigmentResponse';
import {Router} from '@angular/router';

@Component({
  selector: 'scheduler-tabs',
  imports: [
    DatePipe,
    FormsModule,
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './scheduler.component.html',
  styleUrl: './scheduler.component.css'
})
export class SchedulerComponent implements OnInit {

  dateString!: string;
  selectedDate!: Date;
  role: String = "";
  appointments: Appointment[] = [];
  @Input() doctorId!: string;
  selectedAppointment?: Appointment;
  form: FormGroup = new FormGroup({
    observations: new FormControl('')
  });

  menuItems = [
    {value: 1, description: 'PROGRAMADO'},
    {value: 2, description: 'CONFIRMADO'},
    {value: 3, description: 'ASISTIDO'},
    {value: 4, description: 'NO ASISTE'},
    {value: 5, description: 'RECHAZADO'},
  ];


  constructor(private service: AppointmentService, private auth: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    this.selectedDate = new Date()
    this.selectedDate.setHours(0,0,0,0);
    this.dateString = formatDate(this.selectedDate, 'fullDate', "es-ES");
    this.role = this.auth.getRole();
    this.loadAppointment(this.doctorId, this.selectedDate);
  }

  loadAppointment(doctorId: string, selectedDate: Date): void {
    const request: SearchAppointmentRequest = new SearchAppointmentRequest();
    request.doctorId = Number(doctorId);
    request.since = new Date(selectedDate.setHours(0, 0, 0, 0));
    request.until = new Date(selectedDate.setHours(23, 59, 59, 59));
    this.service.searchAppointments(request).subscribe(response => {
      this.appointments = response.appointmentList;
    });
  }

  deleteAppointment(appointment: Appointment): void {
    let date = formatDate(appointment.initDate, 'd/MM/YYYY', "es-ES")
    let hour = formatDate(appointment.initDate, 'HH:mm', "es-ES")

    Swal.fire({
      title: `¿Desea eliminar la cita para el ${date} a las ${hour} ?`,
      icon: "warning",
      confirmButtonColor: "#198754",
      confirmButtonText: "ELIMINAR",
      showCancelButton: true,
      cancelButtonColor: "#dc3545",
      cancelButtonText: "MANTENER",
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.deleteAppointment(appointment.id).subscribe(
          (response) => {
            this.loadAppointment(this.doctorId, this.selectedDate);
            Swal.fire({
              text: "Cita eliminada",
              icon: "success"
            })
          }
        )
      }
    });
  }

  //@Output() download = new EventEmitter<any>();
  downloadAppointmentTextDialog(appointment: Appointment): void {

    Swal.fire({
      title: "Opciones de impresión",
      input: 'select',
      inputOptions: {
        signed: 'Firmado',
        notSigned: 'No firmado',
      },
      inputPlaceholder: 'Seleccione una opción de impresión',
      showCancelButton: true,
      confirmButtonText: 'Generar certificado',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.downloadAppointment(appointment, result.value == 'signed');
      }
    });
  }

  downloadAppointment(appointment: Appointment, signed: boolean): void {
    this.service.generateAppointmentCertificate(appointment.id, signed).subscribe(
      (response) => {
        const contentDisposition = response.headers.get('Content-Disposition');
        const fileName = contentDisposition?.split('=')[1] ?? '';

        const a = document.createElement('a');
        a.href = URL.createObjectURL(response.body!);
        a.download = fileName;
        a.click();
        URL.revokeObjectURL(a.href);
      });
  }

  modifyStatus(appointment: Appointment, value: number): void {
    this.service.modifyStatus(new ModifyStatusAppointmentRequest(appointment.id, value)).subscribe(
      (response) => {
        this.loadAppointment(this.doctorId, this.selectedDate);
      }
    )
  }

  createAppointmentSwal() {
    Swal.fire({
      title: 'Nueva cita',
      html: `
    <div class="mb-3 text-start">
      <label for="dni" class="form-label">DNI</label>
      <input id="dni" type="text" class="form-control"/>
    </div>
    <div class="mb-3 text-start">
      <label for="date" class="form-label">Fecha</label>
      <input id="date" type="date" class="form-control"/>
    </div>
    <div class="mb-3 text-start">
      <label for="initHour" class="form-label">Hora inicio</label>
      <input id="initHour" type="time" class="form-control"/>
    </div>
     <div class="mb-3 text-start">
      <label for="endHour" class="form-label">Hora fin</label>
      <input id="endHour" type="time" class="form-control"/>
    </div>
    <div class="mb-3 text-start">
      <label for="observations" class="form-label">Observaciones</label>
      <input id="observ" type="text" class="form-control"/>
    </div>
    `,
      confirmButtonText: 'Crear',
      focusConfirm: false,
      showCloseButton: true,
      showClass: {
        popup: ''
      },
      hideClass: {
        popup: ''
      },
      preConfirm: () => {
        const dni = (document.getElementById('dni') as HTMLInputElement).value;
        const date = (document.getElementById('date') as HTMLInputElement).value;
        const initHour = (document.getElementById('initHour') as HTMLInputElement).value;
        const endHour = (document.getElementById('endHour') as HTMLInputElement).value;
        const observations = (document.getElementById('observ') as HTMLInputElement).value;

        if (!date || !initHour || !endHour) {
          Swal.showValidationMessage('Los campos DNI, fecha, hora de inicio y hora de fin son obligatorios');
          return;
        }

        var init = initHour.split(":");
        var end = endHour.split(":");

        var initDate = new Date(date);
        initDate.setHours(Number(init[0]), Number(init[1]));

        var endDate = new Date(date);
        endDate.setHours(Number(end[0]), Number(end[1]));

        if (initDate.getTime() >= endDate.getTime()) {
          Swal.showValidationMessage('La hora de fin no puede ser anterior o igual a la hora de inicio')
        }

        return new SaveAppointmentRequest(dni, this.auth.getId(), initDate, endDate, observations, 1);
      }
    }).then(result => {
      if (result.isConfirmed) {
        this.service.saveAppointment(result.value).subscribe(r => {
          this.loadAppointment(this.doctorId, this.selectedDate);
        })
      }
    });
  }

  openEditAppointmentModal(appointment: Appointment) {
    this.selectedAppointment = appointment;

    this.form.patchValue({
      observations: appointment.observations || '',
    });
  }

  saveChanges() {
    if (this.selectedAppointment) {
      const updated = this.form.value;

      this.selectedAppointment.observations = updated.observations;

      let appointmentRequest = new ModifyAppointmentRequest(
        this.selectedAppointment.id,
        this.selectedAppointment.patient.id,
        this.selectedAppointment.doctor.id,
        this.selectedAppointment.initDate,
        this.selectedAppointment.endDate,
        this.selectedAppointment.observations,
        this.selectedAppointment.status.id);

      this.service.modifyAppointment(appointmentRequest).subscribe(r => {
        this.loadAppointment(this.doctorId, this.selectedDate);

        this.form.reset();

        const modalElement = document.getElementById('close-modal-tratamientos');
        if (modalElement) {
          modalElement.click();
        }
      });
    }
  }

  toPreviousDay() {
    let day = this.selectedDate.getDate() - 1;
    this.selectedDate.setDate(day)
    this.selectedDate.setHours(0,0,0,0);
    this.dateString = formatDate(this.selectedDate, 'fullDate', "es-ES");
    this.loadAppointment(this.doctorId, this.selectedDate);
  }

  toNextDay() {
    let day = this.selectedDate.getDate() + 1;
    this.selectedDate.setDate(day)
    this.dateString = formatDate(this.selectedDate, 'fullDate', "es-ES");
    this.loadAppointment(this.doctorId, this.selectedDate);
  }

  isYellow(statusAppointment: string): boolean {
    return "1".includes(statusAppointment);
  }

  isBlue(statusAppointment: string): boolean {
    return "2".includes(statusAppointment);
  }

  isGreen(statusAppointment: string): boolean {
    return "3".includes(statusAppointment);
  }

  isRed(statusAppointment: string): boolean {
    return "4".includes(statusAppointment);
  }

  isGray(statusAppointment: string): boolean {
    return "5".includes(statusAppointment);
  }

  goToPatientsDetails(id: string) {
    this.router.navigate(['dashboard/patients', id]);
  }

}
