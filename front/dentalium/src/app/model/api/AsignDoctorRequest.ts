export class AsignDoctorRequest {

  patientId: string;
  doctorId: string;


  constructor(patientId: string, doctorId: string) {
    this.patientId = patientId;
    this.doctorId = doctorId;
  }
}
