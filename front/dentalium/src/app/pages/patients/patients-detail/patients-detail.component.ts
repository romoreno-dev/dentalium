import {Component, OnInit, ViewChild} from '@angular/core';
import {AppointmentComponent} from '../../../components/appointment/appointment.component';
import {ActivatedRoute} from '@angular/router';
import {AuthService} from '../../../services/auth.service';
import {PatientService} from '../../../services/patient.service';
import {User} from '../../../model/api/SearchUsersResponse';
import {StudiesComponent} from '../../../components/studies/studies.component';
import {StudyService} from '../../../services/study.service';
import Swal from 'sweetalert2';
import {FormsModule} from '@angular/forms';
import {BudgetComponent} from '../../../components/budgets/budget.component';

@Component({
  selector: 'app-patients-detail',
  imports: [
    AppointmentComponent,
    StudiesComponent,
    FormsModule,
    BudgetComponent,
  ],
  templateUrl: './patients-detail.component.html',
  styleUrl: './patients-detail.component.css'
})
export class PatientsDetailComponent implements OnInit {

  patientId: string = '';
  role!: string;
  menuOptions = [
    {value: 1, description: 'Perfil'},
    {value: 2, description: 'Frontal'},
    {value: 3, description: 'Sonrisa'},
    {value: 4, description: 'Oclusal superior'},
    {value: 5, description: 'Intraoral derecha'},
    {value: 6, description: 'Intraoral frontal'},
    {value: 7, description: 'Intraoral izquierdo'},
    {value: 8, description: 'Oclusal inferior'},
    {value: 9, description: 'DICOM'},
  ];
  patient!: User;
  selectedFile: File | null = null;
  selectedType: string | undefined;

  @ViewChild(AppointmentComponent) appointmentComponent!: AppointmentComponent;
  @ViewChild(StudiesComponent) studyComponent!: StudiesComponent;
  @ViewChild(BudgetComponent) budgetComponent!: BudgetComponent;

  constructor(private activeRoute: ActivatedRoute, private patientService: PatientService, private authService: AuthService,
              private studyService: StudyService) {
  }

  ngOnInit() {
    this.role = this.authService.getRole()
    this.activeRoute.params.subscribe(params => {
      this.patientId = params["id"]
      this.patientService.searchPatientInfo(this.patientId).subscribe(r => {
        this.patient = r.users[0]
      })
    })
  }

  saveAppointment() {
    if (this.appointmentComponent) {
      this.appointmentComponent.createAppointmentSwal()
    }
  }

  createBudget() {
    this.budgetComponent.createBudget();
  }

  uploadDocument() {
    if (this.selectedFile && this.selectedType) {

      let extension = this.selectedFile.name.split(".")[1].toLowerCase();
      if ((!this.selectedType.includes('9') && !"jpeg".includes(extension)) || (this.selectedType.includes('9') && !"dcm".includes(extension))) {
        Swal.fire({
          text: `La extensión del fichero no es válida para este tipo de estudio (jpeg para fotografías, dcm para DICOM)`,
          icon: "warning"
        })
        return;
      }

      const formData = new FormData();
      formData.append('file', this.selectedFile, this.selectedFile.name);
      formData.append('type', this.selectedType);
      formData.append('userId', this.patientId);

      this.studyService.uploadStudy(formData).subscribe(next => {

        let idSelected = Number(this.selectedType)
        if (idSelected < 9) {
          this.cleanFile()
          this.selectedType = (++idSelected).toString();
        } else {
          this.cleanBoth()
          const modalElement = document.getElementById('closeButton');
          if (modalElement) {
            modalElement.click();
          }
        }

        this.studyComponent.loadMedicalStudies();
        Swal.fire({
          text: `Estudio subido con éxito`,
          icon: "success"
        })
      }, error => {
        this.cleanBoth()
        Swal.fire({
          text: `Se produjo un error al subir el fichero`,
          icon: "warning"
        })
      });
    }
  }

  onFileChange(event: any) {
    this.selectedFile = event.target.files[0];
    if (!(this.selectedFile instanceof File)) {
      console.error('El archivo no es válido');
    }
  }

  // Limpiar el formulario
  cleanBoth(): void {
    this.cleanType()
    this.cleanFile()
  }

  cleanType() {
    this.selectedType = undefined;
  }

  cleanFile() {
    const fileInput = <HTMLInputElement>document.getElementById('uploadFileComponent');
    if (fileInput) {
      fileInput.value = '';
    }
  }
}
