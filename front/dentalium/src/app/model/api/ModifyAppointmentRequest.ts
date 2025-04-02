export class ModifyAppointmentRequest {
  id: number;
  patientId: string;
  doctorId: string;
  since: Date;
  until: Date;
  observations: string;
  statusCode: string;


  constructor(id: number, patientId: string, doctorId: string, since: Date, until: Date, observations: string, statusCode: string) {
    this.id = id;
    this.patientId = patientId;
    this.doctorId = doctorId;
    this.since = since;
    this.until = until;
    this.observations = observations;
    this.statusCode = statusCode;
  }
}
