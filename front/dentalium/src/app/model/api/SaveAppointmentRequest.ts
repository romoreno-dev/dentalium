export class SaveAppointmentRequest {
  patientId: string;
  doctorId: string;
  since: Date;
  until: Date;
  observations: string;
  statusCode: number;

  constructor(patientId: string, doctorId: string, since: Date, until: Date, observations: string, statusCode: number) {
    this.patientId = patientId;
    this.doctorId = doctorId;
    this.since = since;
    this.until = until;
    this.observations = observations;
    this.statusCode = statusCode;
  }
}
