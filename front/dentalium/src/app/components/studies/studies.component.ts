import {Component, Input} from '@angular/core';
import {MedicalStudy} from '../../model/api/ListMedicalStudiesResponse';
import {StudyService} from '../../services/study.service';
import {AuthService} from '../../services/auth.service';
import Swal from 'sweetalert2';
import {DicomAttribute} from '../../model/api/DicomResponse';
import {DatePipe, formatDate} from '@angular/common';
import {ModifyStatusAppointmentRequest} from '../../model/api/ModifyStatusAppointmentRequest';

@Component({
  selector: 'studies-tab',
  imports: [
    DatePipe
  ],
  templateUrl: './studies.component.html',
  styleUrl: './studies.component.css'
})
export class StudiesComponent {

  role!: String;
  medicalStudies: MedicalStudy[] = [];
  loading: boolean = true;
  @Input() patientId!: string;

  constructor(private service: StudyService, protected auth: AuthService) {
  }

  ngOnInit(): void {
    this.role = this.auth.getRole();
    this.loadMedicalStudies();
  }

  loadMedicalStudies(): void {
    this.service.searchStudies(this.patientId).subscribe(response => {
      this.medicalStudies = response.medicalStudies.sort(
        (a, b) => new Date(b.date).getTime() - new Date(a.date).getTime()
      );
      this.loading = false;
    })
  }

  downloadStudy(study: MedicalStudy): void {
    this.service.downloadStudy(study.id).subscribe(
      (response) => {
        const url = URL.createObjectURL(response.body!);

        if (study.studyType.id == 9) {
          const contentDisposition = response.headers.get('Content-Disposition');
          const fileName = contentDisposition?.split('=')[1] ?? '';

          const a = document.createElement('a');
          a.href = url;
          a.download = fileName;
          a.click();
          URL.revokeObjectURL(a.href);
        } else {
          window.open(url, '_blank');
        }
      });
  }

  showStudy(study: MedicalStudy): void {
    if (study.studyType.id == 9) {
      this.service.downloadDicomStudy(study.id).subscribe(response => {
        this.showImage(response.body!, study.studyType.description);
      });
    } else {
      this.service.downloadStudy(study.id).subscribe(response => {
        this.showImage(response.body!, study.studyType.description);
      });
    }
  }

  deleteStudy(study: MedicalStudy): void {
    Swal.fire({
      title: `¿Desea eliminar ${study.studyType.description} ?`,
      icon: "warning",
      confirmButtonColor: "#dc3545",
      confirmButtonText: "ELIMINAR",
      showCancelButton: true,
      cancelButtonColor: "#198754",
      cancelButtonText: "MANTENER",
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.deleteStudy(study.id).subscribe(
          (response) => {
            this.loadMedicalStudies();
            Swal.fire({
              text: "Estudio eliminado con éxito",
              icon: "info"
            })
          }
        )
      }
    });
  }


  showImage(blob: Blob, description: string): void {
    const imageUrl = URL.createObjectURL(blob);

    Swal.fire({
      imageUrl,
      imageAlt: description,
      showCloseButton: true,
      showConfirmButton: false
    });
  }




  showDicomMetadata(study: MedicalStudy) {
    this.service.downloadDicomData(study.id).subscribe(response => {

      Swal.fire({
        title: 'Metadatos del fichero DICOM',
        html: this.extractMetadata(response.dicomAttributes),
        showCloseButton: true,
        focusConfirm: false,
        confirmButtonText: 'Cerrar'
      });
    });
  }

  extractMetadata(dicomAttributes: DicomAttribute[]): string {
    let text = '<p style="text-align: left">';
    dicomAttributes.forEach(attribute => {
      text += `<b>${attribute.value}</b> : ${attribute.description}<br/>`
    })
    text += '</p>';
    return text;
  }

}
