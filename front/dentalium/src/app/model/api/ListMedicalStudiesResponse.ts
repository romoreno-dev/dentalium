export class ListMedicalStudiesResponse {
  patientId: string;
  medicalStudies: MedicalStudy[];

  constructor() {
    this.patientId = '';
    this.medicalStudies = [];
  }
}

export interface MedicalStudy {
  id: number;
  doctorId: number;
  studyType: StudyType;
  date: Date;
  filename: string;
}

export interface StudyType {
  id: number;
  description: string;
}
