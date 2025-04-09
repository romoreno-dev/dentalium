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

@Component({
  selector: 'appointment-tabs',
  imports: [
    DatePipe,
    NgClass,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './appointment.component.html',
  styleUrl: './appointment.component.css'
})
export class AppointmentComponent implements OnInit {

  role: String = "";
  appointments: Appointment[] = [];
  currentAppointment: boolean = true;
  @Input() patientId!: string;
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


  constructor(private service: AppointmentService, private auth: AuthService) {
  }

  ngOnInit(): void {

    this.currentAppointment = true
    this.role = this.auth.getRole();
    this.loadAppointment(this.currentAppointment, this.patientId);
  }

  loadAppointment(current: boolean, patientId: string): void {
    const request: SearchAppointmentRequest = {patientId};
    const today = new Date(new Date().setHours(0, 0, 0, 0));
    if (current) {
      request.since = today
    } else {
      request.until = today
    }

    this.service.searchAppointments(request).subscribe(response => {
      let list = response.appointmentList;
      this.appointments = this.currentAppointment ? list : response.appointmentList.sort(
        (a, b) => new Date(b.initDate).getTime() - new Date(a.initDate).getTime()
      );
    });
  }

  //@Output() tabChange = new EventEmitter<boolean>();
  tabAppointmentChange(current: boolean) {
    this.currentAppointment = current;
    this.loadAppointment(this.currentAppointment, this.patientId)
  }


  //@Output() confirm = new EventEmitter<any>();
  confirmAppointment(appointment: Appointment): void {
    let date = formatDate(appointment.initDate, 'd/MM/YYYY', "es-ES")
    let hour = formatDate(appointment.initDate, 'HH:mm', "es-ES")

    Swal.fire({
      title: `¿Desea confirmar su cita para el ${date} a las ${hour} ?`,
      icon: "warning",
      confirmButtonColor: "#198754",
      confirmButtonText: "CONFIRMAR",
      showCancelButton: true,
      cancelButtonColor: "#dc3545",
      cancelButtonText: "TODAVÍA NO",
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.modifyStatus(new ModifyStatusAppointmentRequest(appointment.id, 2)).subscribe(
          (response) => {
            this.loadAppointment(this.currentAppointment, this.patientId);
            Swal.fire({
              title: "Cita confirmada",
              text: `Le esperamos el ${date} a las ${hour}`,
              icon: "success"
            })
          }
        )
      }
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
            this.loadAppointment(this.currentAppointment, this.patientId);
            Swal.fire({
              text: "Cita eliminada",
              icon: "success"
            })
          }
        )
      }
    });
  }

  callOffAppointment(appointment: Appointment): void {

    let date = formatDate(appointment.initDate, 'd/MM/YYYY', "es-ES")
    let hour = formatDate(appointment.initDate, 'HH:mm', "es-ES")

    Swal.fire({
      title: `¿Desea rechazar su cita para el ${date} a las ${hour} ?`,
      icon: "warning",
      confirmButtonColor: "#dc3545",
      confirmButtonText: "RECHAZAR",
      showCancelButton: true,
      cancelButtonColor: "#198754",
      cancelButtonText: "MANTENER",
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.modifyStatus(new ModifyStatusAppointmentRequest(appointment.id, 5)).subscribe(
          (response) => {
            this.loadAppointment(this.currentAppointment, this.patientId);
            Swal.fire({
              title: "Cita rechazada",
              text: "Si desea obtener una nueva cita, póngase en contacto con Dentalium",
              icon: "info"
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
        this.loadAppointment(this.currentAppointment, this.patientId);
        Swal.fire({
          text: `Estado modificado`,
          icon: "info",
          showClass: {
            popup: ''
          },
          hideClass: {
            popup: ''
          },
        })
      }
    )
  }

  createAppointmentSwal() {
    Swal.fire({
      title: 'Nueva cita',
      html: `
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
      <input id="observations" type="text" class="form-control"/>
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
        const date = (document.getElementById('date') as HTMLInputElement).value;
        const initHour = (document.getElementById('initHour') as HTMLInputElement).value;
        const endHour = (document.getElementById('endHour') as HTMLInputElement).value;
        const observations = (document.getElementById('observations') as HTMLInputElement).value;

        if (!date || !initHour || !endHour) {
          Swal.showValidationMessage('Los campos fecha, hora de inicio y hora de fin son obligatorios');
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

        return new SaveAppointmentRequest(this.patientId, this.auth.getId(), initDate, endDate, observations, 1);
      }
    }).then(result => {
      if (result.isConfirmed) {
        this.service.saveAppointment(result.value).subscribe(r => {
          this.loadAppointment(this.currentAppointment, this.patientId);
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
        this.loadAppointment(this.currentAppointment, this.patientId);

        this.form.reset();

        const modalElement = document.getElementById('close-modal-tratamientos');
          if (modalElement) {
            modalElement.click();
          }
        });
    }
  }

}
