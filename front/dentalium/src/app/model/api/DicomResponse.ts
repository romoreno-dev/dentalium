export interface DicomResponse {
  medicalStudy: MedicalStudy;
  dicomAttributes: DicomAttribute[];
  base64Content: string;
}

export interface DicomAttribute {
  value: string;
  description: string;
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
